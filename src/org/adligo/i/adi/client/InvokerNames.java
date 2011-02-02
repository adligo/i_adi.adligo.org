package org.adligo.i.adi.client;

/**
 * note use these names to obtain a I_Invoker from the Registry on JME
 * or from the GRegistry on JSE and GWT
 * (
 * @author scott
 *
 */
public class InvokerNames {
	private static final String BASE = "org.adligo.i.adi.";

	/**
	 * allows obtaining a object cache from the 
	 * (JCache or simple cache implemented in this package)
	 * accepts a String (pathlike structure ie /subsystem/cacheName ) returns a Object
	 */
	public static final String CACHE_READER = BASE + "cache_reader";
	/**
	 * allows manipulation (write replace) of the Cache (JCache or simple cache implemented in this package)
	 * accepts a CacheWriterToken returns a Boolean
	 */
	public static final String CACHE_WRITER = BASE + "cache_writer";
	/**
	 * allows manipulation (removal) of items from the Cache
	 * accepts a CacheRemoverToken returns a Boolean
	 */
	public static final String CACHE_REMOVER = BASE + "cache_remover";
	
	/**
	 * allows manipulation (set, update, removal) of long term memory
	 * accepts a MemoryWriterToken returns a boolean
	 */
	public static final String MEMORY_WRITER = BASE + "memory_writer";
	
	/**
	 * allows retrieval of objects long term memory
	 * accepts a String returns a Object (client code must downcast)
	 */
	public static final String MEMORY_READER = BASE + "memory_reader";
	
	/**
	 * returns something that returns System.currentTimeMillis()
	 * stubbed out this way for unit tests of code which involve time
	 * accepts anything (Object) input has no effect
	 * returns a Long (the current System.currentTimeMillis())
	 */
	public static final String CLOCK = BASE + "clock";
	
	/**
	 * sends something to System.out
	 * stubbed out this way for unit tests of code which involves 
	 * printing to the console
	 * accepts a String returns null (return object should be ignored)
	 */
	public static final String OUT = BASE + "out";
	/**
	 * sends something to System.err
	 * stubbed out this way for unit tests of code which involves 
	 * printing to the console
	 * accepts a String returns null (return object should be ignored)
	 */
	public static final String ERR = BASE + "err";
	
	/**
	 * 
	 * for END USER (admin NOT programmer) configuration information 
	 * about the system
	 * GWT does not use this since the Configuration 
	 * must be loaded through async calls (and adi is a synchronous api)
	 * j2se and j2me systems (subsystems) should use this to obtain
	 * Configuration information.
	 * 
	 * It is preferable to have the END USER (admin NOT programmer) 
	 * configuration information come from a place that they can easily 
	 * change it, common conventions seem to be
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
	 * accepts a Class 
	 * returns a implementation of the class ie
	 * I_ModelsCoreConstants constants = (I_ModelsCoreConstants) 
	 *					CONSTANTS_FACTORY.invoke(I_ModelsCoreConstants.class);
	 */
	public static final String CONSTANTS_FACTORY = BASE + "constants_factory";
}
