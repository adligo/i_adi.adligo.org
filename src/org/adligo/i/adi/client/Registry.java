package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.I_Iterator;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;


/**
 * this is the begining of the org.adligo 2.0
 * it is intended as
 * a end all be all wrapper for 
 * stateless threadsafe code
 * 
 * mostly this is storage code (could be Hibernate, Jdbc DAOs,
 * SearchEngine stuff (Lucene), FileSystem stuff , 
 * stuff cached in RAM from some of the previous sources exc..)
 * Of course one of the goals is to be able to swap things so
 * if you wrote Jdbc and then decide you like Hibernate for 
 * something you can swap it.  Or if you wrote something in Hibernate
 * and then decide it needs to move to a Ldap server, and then 
 * change your mind again and decide it needs to stay in RAM.
 *
 * This idea sort of evolved from the 
 * i_persistence interfaces which were a inital attempt at doing 
 * this sort of thing.  However they got somewhat verbose and were to
 * specific to databases.
 * 
 * This set of interfaces are much more adaptable,
 * for instance if you have a I_Invoker
 * for "getPersons"  you may start with 
 * accepting I_TemplateParams to provide the user
 * with a large taximony landsacpe for all sorts of 
 * extendable advanced search screens.
 * 
 * Later on you may think, hey what about 
 * per compiling my queries and doing a lookup of single
 * object on the primary key.  So with this api
 * you can use Polymorphism
 * to pass in a Integer for the precompiled query 
 * or a I_TemplateParams object for a TreeFinder style
 * Query.  exc....
 * 
 * @author scott
 *
 */
public class Registry  {
	private static final Log log = LogFactory.getLog(Registry.class);
	/**
	 * only print out errors about running from tests 
	 * when not running tests
	 */
	protected static final Log quite_test_log = LogFactory.getLog(Registry.class.getName() + "_tests");
	/**
	 * <String,I_Invoker> 
	 * CLDC 2.0
	 */
	private static I_Map methods = null;
	private static CheckedRegistry checked = new CheckedRegistry();
	
	
	/**
	 * common init code for this class
	 * 
	 */
	private static void init() { 
		methods = MapFactory.create();
		
		
		I_Iterator it = ProxyInvoker.getPreInitInvokers();
		while (it.hasNext()) {
			ProxyInvoker pi = (ProxyInvoker) it.next();
			methods.put(pi.getName(), pi);
		}
		CheckedRegistry.init();
	}
	
	/**
	 * only use for testing
	 */
	static void uninit() {
		if (Registry.quite_test_log.isErrorEnabled()) {
			Exception x = new Exception();
			x.fillInStackTrace();
			Registry.quite_test_log.warn("uninit called from ", x);
		}
		methods = null;
		CheckedRegistry.uninit();
	}

	/**
	 * dynamic locator method to discover your implementations
	 * at run time
	 * 
	 * @param name 
	 * Each usage of this sort of lookup 
	 * will very, and a naming convention should be established
	 * but generally the goal is to keep things simple
	 * so in the adligo code names will look like
	 * 
	 * getPersons
	 * savePersons
	 * findSocks
	 * openJar 
	 * exc...
	 * 
	 * @return
	 */
	public static synchronized I_CheckedInvoker getCheckedInvoker(String p) {
		return checked.getCheckedInvoker(p);
	}


	/**
	 * @see getCheckedInvoker method same idea
	 */
	public static synchronized I_Invoker getInvoker(String p) {
		I_Invoker toRet = null;
		if (methods == null) {
			toRet = ProxyInvoker.getInstance(p);
		} else {
			toRet = (I_Invoker) methods.get(p);
			if (toRet == null) {
				toRet = ProxyInvoker.getInstance(p);
				methods.put(p, toRet);
			} 
		}
		// not sure why this log message doesn't work from GWT???
		if (log.isDebugEnabled()) {
			log.debug("Returning " + toRet + " for key " + p);
		}
		return toRet;
	}
	
	/**
	 * new api for initialization
	 * wouln't replace only sets the first time
	 */
	public static synchronized void addInvokers(I_Map p ) {
		if (log.isDebugEnabled()) {
			log.debug("entering addInvokerDelegates...");
		}
		if (methods == null) {
			init();
		}
		I_Iterator it = p.getIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			I_Invoker invoker = (I_Invoker) p.get(key);
			addInvoker(key, invoker);
		}
		if (log.isDebugEnabled()) {
			log.debug("exiting addInvokerDelegates...");
		}
	}

	public static synchronized void addInvoker(String key, I_Invoker invoker) {
		if (methods == null) {
			init();
		}
		ProxyInvoker pi = (ProxyInvoker) methods.get(key);
		if (pi == null) {
			methods.put(key, new ProxyInvoker(key, invoker));
			if (log.isInfoEnabled()) {
				log.info("addInvoker " + key + " is now " + methods.get(key));
			}
		} else {
			if (pi.getDelegate() == null) {
				pi.setDelegate(invoker);
				if (log.isInfoEnabled()) {
					log.info("addInvoker " + key + " is now " + methods.get(key));
				}
			} else {
				if (log.isWarnEnabled()) {
					log.warn("invoker with name " + key + " was NOT replaced when calling addInvoker");
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.info("addInvoker " + key + " is now " + methods.get(key));
		}
	}
	
	public static synchronized void addCheckedInvoker(String key, I_CheckedInvoker invoker){
		checked.addCheckedInvoker(key, invoker);
	}
	
	public static synchronized void addOrReplaceInvoker(String key, I_Invoker invoker) {
		if (methods == null) {
			init();
		}
		
		ProxyInvoker pi = (ProxyInvoker) methods.get(key);
		if (pi != null) {
			pi.setDelegate(invoker);
		} else {
			methods.put(key, new ProxyInvoker(key, invoker));
		}
		if (log.isInfoEnabled()) {
			log.info("addOrReplaceInvoker " + key + " is now " + methods.get(key));
		}
	}
	
	public static synchronized void addOrReplaceCheckedInvoker(String key, I_CheckedInvoker invoker) {
		checked.addOrReplaceCheckedInvoker(key, invoker);
	}
	
	/**
	 * new api for initalization
	 */
	public static synchronized void addCheckedInvokers(I_Map p) {
		checked.addCheckedInvokers(p);
	}
	
	/**
	 * will add if not present
	 * or replace if there
	 * @param p
	 */
	public static synchronized void addOrReplaceInvokers(I_Map p ) {
		if (log.isInfoEnabled()) {
			log.info("entering replaceInvokerDelegates...");
		}
		if (methods == null) {
			init();
		}
		I_Iterator it = p.getIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			I_Invoker value = (I_Invoker) p.get(key);
			ProxyInvoker pi = (ProxyInvoker) methods.get(key);
			if (pi == null) {
				pi = new ProxyInvoker(key);
				pi.setDelegate(value);
				methods.put(key, pi);
			} else {
				if (pi.getDelegate() == null) {
					addOrReplaceInvoker(key, (I_Invoker) p.get(key));
				} else {
					pi.setDelegate((I_Invoker) p.get(key));
				}
			}
			
		}
		if (log.isInfoEnabled()) {
			log.info("exiting replaceInvokerDelegates...");
		}
	}
	
	
	/**
	 * will add if not present
	 * or replace if there
	 * @param p
	 */
	public static synchronized void addOrReplaceCheckedInvokers(I_Map p ) {
		checked.replaceCheckedInvokerDelegates(p);
	}
	
	protected synchronized static void clear() {
		if (methods != null) {
			methods.clear();
		}
		ProxyInvoker.clearPreInitInvokers();
		CheckedRegistry.clear();
	}
	
	public static void debug() {
		if (log.isDebugEnabled()) {
			log.debug("Methods:\n");
			if (methods != null) {
				I_Iterator it = methods.keys();
				while (it.hasNext()) {
					Object obj = it.next();
					log.debug(obj);
				}
			} else {
				log.debug(" are null ");
			}
			log.debug("\n\nChecked Methods:\n");
			checked.debug();
		}
	}
}
