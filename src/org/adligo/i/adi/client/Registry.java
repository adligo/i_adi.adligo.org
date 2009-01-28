package org.adligo.i.adi.client;

import org.adligo.i.util.client.ArrayCollection;
import org.adligo.i.util.client.CollectionFactory;
import org.adligo.i.util.client.I_Collection;
import org.adligo.i.util.client.I_Iterator;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

import org.adligo.i.adi.client.cache.Cache;
import org.adligo.i.adi.client.cache.CacheReader;
import org.adligo.i.adi.client.cache.CacheWriter;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;


/**
 * this is the begining of the org.adligo 2.0
 * it is intended as
 * a end all be all wrapper for 
 * stateless threadsafe code
 * 
 * It is intended to be a general api for hiding;
 * 1) The location of the code, is it calling local or remote code?
 * 2) The implementation details of the code so that the code can have
 * 		swappable parts
 * 3) It is intended to run on J2ME, GWT and J2SE, I have borrowed the GWT
 *    remote (RPC) plumbing as the api since it is the most efficient
 *    and will have the least blocking and waiting
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
public final class Registry  {
	private static final Log log = LogFactory.getLog(Registry.class);
	
	/**
	 * <String,I_Invoker> 
	 * CLDC 2.0
	 */
	private static I_Map methods;
	private static I_Collection preInitProxyMethods = new ArrayCollection();
	
	
	protected Registry() { 
		preInitProxyMethods.add(new ProxyInvoker(Cache.CACHE_READER, new CacheReader()));
		preInitProxyMethods.add(new ProxyInvoker(Cache.CACHE_WRITER, new CacheWriter()));
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
	 * Note since one of the objectives of Adi is to hide the location of the code
	 * (ie is it remote or local code) I have chosen googles RPC api
	 * since I feel it best handles remote code
	 * and can also be used to handle local code
	 * 
	 * getPersons
	 * savePersons
	 * findSocks
	 * openJar 
	 * exc...
	 * 
	 * @return
	 */
	public static synchronized I_InvokerAsync getInvoker(String p) {
		I_InvokerAsync toRet = null;
		if (methods == null) {
			toRet = new ProxyInvoker(p);
			preInitProxyMethods.add(toRet);
		} else {
			toRet = (I_InvokerAsync) methods.get(p);
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + toRet + " for key " + p);
		}
		return toRet;
	}
	
	/**
	 * new api for initalization
	 * wouln't replace only sets the first time
	 */
	public static synchronized void addInvokerDelegates(I_Map p ) {
		if (log.isDebugEnabled()) {
			log.debug("entering addInvokerDelegates...");
		}
		if (methods == null) {
			methods = MapFactory.create();
			if (preInitProxyMethods.size() == 0) {
				I_Iterator it = p.getIterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					I_Invoker pi = (I_Invoker) methods.get(key);
					if (pi == null) {
						I_InvokerAsync invoker = (I_InvokerAsync) p.get(key);
						if (log.isInfoEnabled()) {
							log.info("putting invoker " + key + " obj " + invoker);
						}
						//everything is always a proxy!
						ProxyInvoker proxy = new ProxyInvoker(key, invoker);
						methods.put(key, proxy);
					}
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("creating methods object preInitProxyMethods has " + 
							preInitProxyMethods.size());
				}
				
				
				I_Iterator it = preInitProxyMethods.getIterator();
				while (it.hasNext()) {
					ProxyInvoker pi = (ProxyInvoker) it.next();
					methods.put(pi.getName(), pi);
					I_InvokerAsync del = (I_InvokerAsync) p.get(pi.getName()); 
					pi.setDelegate(del);
					if (log.isInfoEnabled()) {
						log.info("put " + del  + " in " + pi);
					}
				}
				
			}
		} else {
			//methods already exist
			I_Iterator it = p.getIterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				ProxyInvoker pi = (ProxyInvoker) methods.get(key);
				I_InvokerAsync invoker = (I_InvokerAsync) p.get(key);
				if (pi != null) {
					if (pi.getDelegate() == null) {
						pi.setDelegate(invoker);
					}
				} else {
					methods.put(key, invoker);
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("exiting addInvokerDelegates...");
		}
	}
	
	/**
	 * 
	 * @return a iterator of ProxyInvoker objects that have been requested
	 * by the static initalizers
	 * 
	 * used to 
	 */
	public static I_Iterator getPreInitIterator() {
		return preInitProxyMethods.getIterator();
	}
	
}
