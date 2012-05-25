package org.adligo.i.adi.client;

public interface I_Cacheable {
	/**
	 * return the estimated number of bytes this instance takes
	 * can be used to make decisions about memory in the cache api
	 * @return
	 */
	public int getMemsize();
}
