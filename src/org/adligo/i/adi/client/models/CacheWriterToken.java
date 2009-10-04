package org.adligo.i.adi.client.models;

public class CacheWriterToken {
	public static final short SET_ALWAYS = 0;
	public static final short ADD_ONLY_IF_NOT_PRESENT = 1;
	public static final short REPLACE_ONLY_IF_PRESENT = 2;
	
	public static final int ONE_MINUTE_MINUTES = 5 * 60 * 1000;
	public static final int FIVE_MINUTES = 5 * 60 * 1000;
	public static final int TEN_MINUTE_MINUTES = 10 * 60 * 1000;
	public static final int ONE_HOUR = 60 * 60 * 1000;
	public static final int ONE_DAY = 24 * 60 * 60 * 1000;
	
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
	private short setPolicy = SET_ALWAYS;

	/**
	 * the number of seconds before the item should be
	 * evicted from the cache (defaults to never)
	 */
	private Integer expiration_delta;
	
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
	public Integer getExpiration_delta() {
		return expiration_delta;
	}
	public void setExpiration_delta(Integer expirationDelta) {
		expiration_delta = expirationDelta;
	}
	public short getSetPolicy() {
		return setPolicy;
	}
	public void setSetPolicy(short setPolicy) {
		this.setPolicy = setPolicy;
	}
	
	
}
