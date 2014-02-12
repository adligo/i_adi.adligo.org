package org.adligo.i.adi.shared.light;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.models.MemoryValue;

/**
 * @see Memory
 * @author scott
 *
 */
public final class MemoryReader implements I_Invoker {
	static final MemoryReader INSTANCE = new MemoryReader();
	
	private MemoryReader() {}
	
	public Object invoke(Object key) {
		MemoryValue value = (MemoryValue) Memory.items.get(key);
		if (value == null) {
			return null;
		}
		return value.getValue();
	}

}
