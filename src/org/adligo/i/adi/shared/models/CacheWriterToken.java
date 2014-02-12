package org.adligo.i.adi.shared.models;

/**
 * a token (input argument like a quarter for a video game)
 * to write cache
 * 
 * NOTE expiration_delta and increment_delta were removed
 * as they were info for the JCache Api 
 * (which this api can and does wrap)
 * 
 * expiration_delta was removed because it isn't important for
 * caching client code shoulnd't expect things to stay in the
 * cache for any particular amout of time.  If the JVM is running
 * out of memory Cache is the first thing to clean out.
 * 
 * increment_delta this is a way to get a counter from memory
 * but that isn't really scaleable, use some disk api (aka a database)
 * http://www.mentata.com/ldaphttp/examples/gospel/increment.htm
 * or oracle, postgres, mssql exc sequence
 * 
 * @author scott
 *
 */
public class CacheWriterToken {
	public static final short SET_ALWAYS = 0;
	public static final short ADD_ONLY_IF_NOT_PRESENT = 1;
	public static final short REPLACE_ONLY_IF_PRESENT = 2;

	
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
	public short getSetPolicy() {
		return setPolicy;
	}
	public void setSetPolicy(short setPolicy) {
		this.setPolicy = setPolicy;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CacheWriterToken [name=");
		sb.append(name);
		sb.append(",value=");
		sb.append(value);
		sb.append(",setPolicy=");
		sb.append(setPolicy);
		sb.append(",setPolicy=");
		sb.append(setPolicy);
		sb.append("]");
		return sb.toString();
	}
}
