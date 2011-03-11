package org.adligo.i.adi.client.models;

import org.adligo.i.adi.client.light.Memory;

/**
 * class to edit long term memory
 * @see Memory
 * 
 * @author scott
 *
 */
public class MemoryWriterToken {
	private String key;
	private Object value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
