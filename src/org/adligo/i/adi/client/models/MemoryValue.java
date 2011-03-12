package org.adligo.i.adi.client.models;

public class MemoryValue {
	private Object value;
	private Object owner;
	
	public MemoryValue(Object pValue, Object pOwner) {
		value = pValue;
		owner = pOwner;
	}
	
	public Object getValue() {
		return value;
	}
	public Object getOwner() {
		return owner;
	}
}
