package org.adligo.i.adi.client.heavy;


import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.MemoryValue;
import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.i.util.client.StringUtils;

/**
 * @see HeavyMemory
 * @author scott
 *
 */
public final class HeavyMemoryWriter implements I_Invoker {
	public static final String ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT = "Only the owner of a MemoryValue is allowed to modify it.";
	public static final String MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN = 
						"MemoryWriter requires a " + MemoryWriterToken.class.getName() + " and was passed ";
	public static final String MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN_AND_WAS_NULL =
		MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN + " and was passed null.";
	public static final String MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY = "MemoryWriter requires a non empty key.";
	
	static final HeavyMemoryWriter INSTANCE = new HeavyMemoryWriter();
	
	private HeavyMemoryWriter() {}
	
	public Object invoke(Object token) {
		try {
			return invoke((MemoryWriterToken) token);
		} catch (ClassCastException x) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN + token);
		}
	}
	
	private Boolean invoke(MemoryWriterToken token) {
		if (token == null) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN_AND_WAS_NULL);
		}
		String key = token.getKey();
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY);
		}
		Object obj = token.getValue();
		Object owner = token.getOwner();
		MemoryValue value = (MemoryValue) HeavyMemory.items.get(key);
		if (value == null) {
			write(key, obj, owner);
		} else {
			Object currentOwner = value.getOwner();
			if (currentOwner == null) {
				write(key, obj, owner);
			} else {
				if (currentOwner != owner) {
					throw new IllegalArgumentException(ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT);
				} else {
					write(key, obj, owner);
				}
			}
		}
		return Boolean.TRUE;
	}

	private void write(String key, Object obj, Object owner) {
		//object may be null if you want to remove it!
		if (obj == null) {
			HeavyMemory.items.remove(key);
		} else {
			HeavyMemory.items.put(key, new MemoryValue(obj,owner));
		}
	}
}
