package org.adligo.i.adi.client;

public class CacheEditToken {
	/**
	 * should contain a path like name
	 * matching the project ie
	 * 
	 * /com/adligo/ajws/config
	 * 
	 * A list of names as constants should go at the project level as well
	 * with comments
	 * 
	 */
	private String name = "";
	private Object value = new Object();
	
	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
