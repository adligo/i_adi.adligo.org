package org.adligo.i.adi.client.heavy;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.MemoryValue;

/**
 * @see HeavyMemory
 * @author scott
 *
 */
public final class HeavyMemoryReader implements I_Invoker {
	static final HeavyMemoryReader INSTANCE = new HeavyMemoryReader();
	
	private HeavyMemoryReader() {}
	
	public Object invoke(Object key) {
		MemoryValue value = (MemoryValue) HeavyMemory.items.get(key);
		if (value == null) {
			return null;
		}
		return value.getValue();
	}

}
