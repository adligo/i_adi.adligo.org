package org.adligo.i.adi.client.light;


import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.i.util.client.StringUtils;

/**
 * @see Memory
 * @author scott
 *
 */
public class MemoryWriter implements I_Invoker {
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
	
	private Boolean invoke(MemoryWriterToken token) {
		if (token == null) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN_AND_WAS_NULL);
		}
		String key = token.getKey();
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY);
		}
		Object obj = token.getValue();
		//object may be null if you want to remove it!
		if (obj == null) {
			Memory.items.remove(key);
		} else {
			Memory.items.put(key, obj);
		}
		return Boolean.TRUE;
	}
}