package org.adligo.i.adi.shared;

public interface I_Cacheable {
	public static final int OBJECT = 8;
	public static final int BOOLEAN = 1;
	public static final int BYTE = 1;
	public static final int SHORT = 2;
	public static final int FLOAT = 4;
	public static final int INT = 4;
	public static final int DOUBLE = 8;
	public static final int LONG = 4;
	
	/**
	 * return the estimated number of bytes this instance takes
	 * can be used to make decisions about memory in the cache api.
	 * Note this should be a deep calculation, (references should 
	 * calculate their size)
	 * 
	 * Note classes like String and Char depend on their charset
	 * assume UTF-8 
	 * String.getBytes().length
	 * 
	 * @return
	 */
	public int getMemsize();
	
	
}
