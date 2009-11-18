package org.adligo.i.adi.client;

public class InvokerNames {
	private static final String BASE = "org.adligo.i.adi.";
	/**
	 * these are the traditional constants of the cache reader and writer
	 */
	public static final String CACHE_READER = BASE + "cache_reader";
	public static final String CACHE_WRITER = BASE + "cache_writer";
	public static final String CACHE_REMOVER = BASE + "cache_remover";
	
	/**
	 * returns something that returns System.currentTimeMillis()
	 * stubbed out this way for unit tests of code which involve time
	 */
	public static final String CLOCK = BASE + "clock";
	
	/**
	 * sends something to System.out
	 * stubbed out this way for unit tests of code which involves 
	 * printing to the console
	 */
	public static final String OUT = BASE + "out";
	
	/**
	 * 
	 * for END USER (admin NOT programmer) configuration information 
	 * about the system
	 * GWT does not use this since the Configuration 
	 * must be loaded through async calls (and adi is a synchronus api)
	 * j2se and j2me systems (subsystems) should use this to obtain
	 * configuraiton information.
	 * 
	 * It is preferrable to have the END USER (admin NOT programmer) 
	 * configuration information come from a place that they can easliy 
	 * change it, common convetions seem to be
	 * 
	 * SYSTEM_HOME (ie JBOSS_HOME, ANT_HOME, HUDSON_HOME exc) 
	 * conf/someFile(.xml, .txt, .properties)
	 * so that the administrator can set up connection strings
	 * and other things that are needed to run the system
	 * (configuration changeable at runtime is usually better 
	 * stored in a DB or other shared storage system [LDAP server])
	 * 
	 * Also this invoker can be used as a proxy for runtime configurable 
	 * information, so that a admin can change things at runtime
	 * 
	 * parameter ConfigRequest or 
	 * (J2EE container impl org.adligo.i.adi.server.I_HttpRpcContext<ConfigRequest> ) 
	 * returns some NON MUTABLE Object that was configured
	 *    
	 */
	public static final String CONFIGURATION_PROVIDER = BASE + "configuration_provider";
	
	/**
	 * This should provide a Interface from the interfaces name
	 * for constants similar to;
	 * http://google-web-toolkit.googlecode.com/svn/javadoc/1.5/com/google/gwt/i18n/client/Constants.html
	 * 
	 * Or on other platforms a concrete impl as shown by the (see models_core package)
	 * I_UserValidationConstants
	 * UserValidationEnglishConstants
	 */
	public static final String CONSTANTS_FACTORY = BASE + "constants_factory";
}
