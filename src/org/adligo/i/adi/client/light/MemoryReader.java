package org.adligo.i.adi.client.light;

import org.adligo.i.adi.client.I_Invoker;

/**
 * @see Memory
 * @author scott
 *
 */
public class MemoryReader implements I_Invoker {
	public static final MemoryReader INSTANCE = new MemoryReader();
	
	protected MemoryReader() {}
	
	public Object invoke(Object key) {
		return Memory.items.get(key);
	}

}
