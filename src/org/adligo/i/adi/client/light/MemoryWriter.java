package org.adligo.i.adi.client.light;


import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.MemoryValue;
import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.i.util.client.StringUtils;

/**
 * @see Memory
 * @author scott
 *
 */
public class MemoryWriter implements I_Invoker {
	public static final String ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT = "Only the owner of a MemoryValue is allowed to modify it.";
	
	public static final String MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN = 
						"MemoryWriter requires a " + MemoryWriterToken.class.getName() + " and was passed ";
	public static final String MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN_AND_WAS_NULL =
		MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN + " and was passed null.";
	public static final String MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY = "MemoryWriter requires a non empty key.";
	public static final MemoryWriter INSTANCE = new MemoryWriter();
	
	protected MemoryWriter() {}
	
	public Object invoke(Object token) {
		try {
			return invoke((MemoryWriterToken) token);
		} catch (ClassCastException x) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN + token);
		}
	}
	
	/**
	 * note this is synchronized only for JME
	 *  (synchronization is removed for GWT during gwt compile)
	 *  
	 * @param token
	 * @return
	 */
	private synchronized Boolean invoke(MemoryWriterToken token) {
		if (token == null) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN_AND_WAS_NULL);
		}
		String key = token.getKey();
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY);
		}
		Object obj = token.getValue();
		Object newOwner = token.getOwner();
		MemoryValue currentValue = (MemoryValue) Memory.items.get(key);
		if (currentValue == null) {
			set(key, obj, newOwner);
		} else {
			Object currentOwner = currentValue.getOwner();
			if (currentOwner == null) {
				set(key, obj, newOwner);
			} else {
				if (currentOwner.equals(newOwner)) {
					set(key, obj, newOwner);
				} else {
					throw new IllegalArgumentException(ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT + token);
				}
			}
		}
		return Boolean.TRUE;
	}

	private void set(String key, Object obj, Object newOwner) {
		//object may be null if you want to remove it!
		if (obj == null) {
			Memory.items.remove(key);
		} else {
			MemoryValue value = new MemoryValue(obj, newOwner);
			Memory.items.put(key, value);
		}
	}
}
