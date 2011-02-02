package org.adligo.i.adi.client;

/**
 * @see Memory
 * @author scott
 *
 */
public class MemoryReader implements I_Invoker {
	protected static final MemoryReader INSTANCE = new MemoryReader();
	
	protected MemoryReader() {}
	
	public Object invoke(Object key) {
		return Memory.items.get(key);
	}

}
