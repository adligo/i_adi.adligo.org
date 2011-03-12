package org.adligo.i.adi.client.light;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.MemoryValue;

/**
 * @see Memory
 * @author scott
 *
 */
public class MemoryReader implements I_Invoker {
	public static final MemoryReader INSTANCE = new MemoryReader();
	
	protected MemoryReader() {}
	
	public Object invoke(Object key) {
		MemoryValue value = (MemoryValue) Memory.items.get(key);
		if (value == null) {
			return null;
		}
		return value.getValue();
	}

}
