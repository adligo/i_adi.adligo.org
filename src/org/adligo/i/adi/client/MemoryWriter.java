package org.adligo.i.adi.client;


import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.i.util.client.StringUtils;

/**
 * @see Memory
 * @author scott
 *
 */
public class MemoryWriter implements I_Invoker {
	public static final String MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN = 
						"MemoryWriter requires a " + MemoryWriterToken.class.getName();
	public static final String MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY = "MemoryWriter requires a non empty key.";
	protected static final MemoryWriter INSTANCE = new MemoryWriter();
	
	protected MemoryWriter() {}
	
	public Object invoke(Object token) {
		try {
			return invoke((MemoryWriterToken) token);
		} catch (ClassCastException x) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN);
		}
	}
	
	private Boolean invoke(MemoryWriterToken token) {
		String key = token.getKey();
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException(MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY);
		}
		Object obj = token.getValue();
		//object may be null if you want to remove it!
		if (obj == null) {
			synchronized (Memory.class) {
				Memory.items.remove(key);
			}
		} else {
			synchronized (Memory.class) {
				Memory.items.put(key, obj);
			}
		}
		return Boolean.TRUE;
	}
}
