package org.adligo.i.adi.shared.models;

/**
 * class to edit long term memory
 * @see HeavyMemory
 * 
 * @author scott
 *
 */
public class MemoryWriterToken {
	private String key;
	private Object value;
	/**
	 * optional field for the Memory class
	 * once written a key can only be modified 
	 * by it's owner  (allows for read only clients)
	 */
	private Object owner;
	
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
	public Object getOwner() {
		return owner;
	}
	public void setOwner(Object owner) {
		this.owner = owner;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MemoryWriterToken [key=");
		sb.append(key);
		sb.append(",value=");
		sb.append(value);
		sb.append(",owner=");
		sb.append(owner);
		sb.append("]");
		
		return sb.toString();
	}
}
