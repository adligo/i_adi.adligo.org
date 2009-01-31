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
	/**
	 * <String,I_Invoker> 
	 * CLDC 2.0
	 */
	private static I_Map methods = null;
	/*
	 * <String,I_CheckedInvoker>
	 */
	private static I_Map checkedMethods = null;
	
	private static I_Collection preInitMethods = new ArrayCollection();
	private static I_Collection preInitCheckedMethods = new ArrayCollection();

	/**
	 * call this from a static initalizier on J2SE and J2ME
	 * or from the onLoad of your GWT module
	 */
	private static void init() { 
		methods = MapFactory.create();
		checkedMethods = MapFactory.create();
		methods.put(InvokerNames.CACHE_READER, 
				new ProxyInvoker(InvokerNames.CACHE_READER, new CacheReader()));
		methods.put(InvokerNames.CACHE_WRITER, 
				new ProxyInvoker(InvokerNames.CACHE_WRITER, new CacheWriter()));
		
		I_Iterator it = preInitMethods.getIterator();
		while (it.hasNext()) {
			ProxyInvoker pi = (ProxyInvoker) it.next();
			methods.put(pi.getName(), pi);
		}
		
		it = preInitCheckedMethods.getIterator();
		while (it.hasNext()) {
			ProxyCheckedInvoker pi = (ProxyCheckedInvoker) it.next();
			checkedMethods.put(pi.getName(), pi);
		}
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
			preInitCheckedMethods.add(toRet);
		} else {
			toRet = (I_CheckedInvoker) checkedMethods.get(p);
			if (toRet == null) {
				toRet = new ProxyCheckedInvoker(p);
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
	 * @see getCheckedInvoker method same idea
	 */
	public static synchronized I_Invoker getInvoker(String p) {
		I_Invoker toRet = null;
		if (checkedMethods == null) {
			toRet = new ProxyInvoker(p);
			preInitMethods.add(toRet);
		} else {
			toRet = (I_Invoker) methods.get(p);
			if (toRet == null) {
				toRet = new ProxyInvoker(p);
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
	 * new api for initalization
	 * wouln't replace only sets the first time
	 */
	public static synchronized void addInvokerDelegates(I_Map p ) {
		if (log.isDebugEnabled()) {
			log.debug("entering addInvokerDelegates...");
		}
		if (methods == null) {
			init();
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
		if (log.isDebugEnabled()) {
			log.debug("exiting addInvokerDelegates...");
		}
	}
	/**
	 * new api for initalization
	 */
	public static synchronized void addCheckedInvokerDelegates(I_Map p) {
		if (checkedMethods == null) {
			init();
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
