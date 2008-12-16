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
	protected static final boolean loging = true;

	/**
	 * <String,I_Invoker> 
	 * CLDC 2.0
	 */
	private static I_Map methods;
	/*
	 * <String,I_CheckedInvoker>
	 */
	private static I_Map checkedMethods;
	private static I_Collection preInitProxyMethods = new ArrayCollection();
	private static I_Collection preInitProxyCheckedMethods = new ArrayCollection();
	
	
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
	 * getPersons
	 * savePersons
	 * findSocks
	 * openJar 
	 * exc...
	 * 
	 * @return
	 */
	public static synchronized I_CheckedInvoker getCheckedInvoker(String p) {
		
		I_CheckedInvoker toRet = null;
		if (checkedMethods == null) {
			toRet = new ProxyCheckedInvoker(p);
			preInitProxyCheckedMethods.add(toRet);
		} else {
			toRet = (I_CheckedInvoker) checkedMethods.get(p);
		}
		// not sure why this log message doesn't work???
		if (log.isDebugEnabled()) {
			log.debug("Returning " + toRet + " for checked key " + p);
		}
		return toRet;
	}


	/**
	 * @see getCheckedInvoker method same idea
	 */
	public static synchronized I_Invoker getInvoker(String p) {
		I_Invoker toRet = null;
		if (methods == null) {
			toRet = new ProxyInvoker(p);
			preInitProxyMethods.add(toRet);
		} else {
			toRet = (I_Invoker) methods.get(p);
		}
		// not sure why this log message doesn't work from GWT???
		if (log.isDebugEnabled()) {
			log.debug("Returning " + toRet + " for key " + p);
		}
		if (Registry.loging) {
			System.out.println("Returning " + toRet + " for key " + p);
		}
		return toRet;
	}
	
	/**
	 * new api for initalization
	 * wouln't replace only sets the first time
	 */
	public static synchronized void addInvokerDelegates(I_Map p ) {
		// not sure why this log message doesn't work???
		if (log.isDebugEnabled()) {
			log.debug("entering addInvokerDelegates...");
		}
		if (Registry.loging) {
			System.out.println("entering addInvokerDelegates...");
		}
		if (methods == null) {
			methods = MapFactory.create();
			if (preInitProxyMethods.size() == 0) {
				I_Iterator it = p.getIterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					I_Invoker pi = (I_Invoker) methods.get(key);
					if (pi == null) {
						I_Invoker invoker = (I_Invoker) p.get(key);
						if (log.isInfoEnabled()) {
							log.info("putting invoker " + key + " obj " + invoker);
						}
						methods.put(key, invoker);
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
					I_Invoker del = (I_Invoker) p.get(pi.getName()); 
					pi.setDelegate(del);
					if (log.isInfoEnabled()) {
						log.info("put " + del  + " in " + pi);
					}
				}
				
				preInitProxyMethods.clear();
			}
		} 
		
		I_Iterator it = p.getIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ProxyInvoker pi = (ProxyInvoker) methods.get(key);
			I_Invoker invoker = (I_Invoker) p.get(key);
			if (pi != null) {
				if (pi.getDelegate() == null) {
					pi.setDelegate(invoker);
				}
			} else {
				methods.put(key, invoker);
			}
		}
		// not sure why this log message isn't working?
		if (log.isDebugEnabled()) {
			log.debug("exiting addInvokerDelegates...");
		}
		if (Registry.loging) {
			System.out.println("exiting addInvokerDelegates...");
		}
	}
	/**
	 * new api for initalization
	 */
	public static synchronized void addCheckedInvokerDelegates(I_Map p) {
		if (checkedMethods == null) {
			checkedMethods = MapFactory.create();
			if (preInitProxyCheckedMethods.size() == 0) {
				I_Iterator it = p.getIterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					I_CheckedInvoker pi = (I_CheckedInvoker) checkedMethods.get(key);
					if (pi == null) {
						I_CheckedInvoker invoker = (I_CheckedInvoker) p.get(key);
						if (log.isInfoEnabled()) {
							log.info("putting checked invoker " + key + " obj " + invoker);
						}
						checkedMethods.put(key, invoker);
					}
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("creating checkedMethods object preInitProxycheckedMethods has " + 
							preInitProxyCheckedMethods.size());
				}
				
				
				I_Iterator it = preInitProxyCheckedMethods.getIterator();
				while (it.hasNext()) {
					ProxyCheckedInvoker pi = (ProxyCheckedInvoker) it.next();
					checkedMethods.put(pi.getName(), pi);
					pi.setDelegate((I_CheckedInvoker) p.get(pi.getName()));
					if (log.isInfoEnabled()) {
						log.info("put ProxyCheckedInvoker " + pi);
					}
				}
				
				preInitProxyCheckedMethods.clear();
			}
		} 
		
		I_Iterator it = p.getIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ProxyCheckedInvoker pi = (ProxyCheckedInvoker) checkedMethods.get(key);
			I_CheckedInvoker invoker = (I_CheckedInvoker) p.get(key);
			if (pi != null) {
				if (pi.getDelegate() == null) {
					pi.setDelegate(invoker);
				}
			} else {
				checkedMethods.put(key, invoker);
			}
		}
	}
}
