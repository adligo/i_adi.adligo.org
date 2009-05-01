package org.adligo.i.adi.client;

public class InvokerNames {
	/**
	 * these are the traditional constants of the cache reader and writer
	 */
	public static final String CACHE_READER = "org.adligo.i.adi.cache_reader";
	public static final String CACHE_WRITER = "org.adligo.i.adi.cache_writer";
	/**
	 * this invoker should provide a Map<String,Object>
	 * for configuration information about the system
	 * GWT does not use this since the Configuration 
	 * must be loaded through async calls (and adi is a synchronus api)
	 * j2se and j2me systems (subsystems) should use this to obtain
	 * configuraiton information.
	 */
	public static final String CONFIGURATION_PROVIDER = "org.adligo.i.adi.configuration_provider";
}
