package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.I_Iterator;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

public class CheckedRegistry {
	private static final Log log = LogFactory.getLog(CheckedRegistry.class);
	
	/*
	 * <String,I_CheckedInvoker>
	 */
	private static I_Map checkedMethods = null;
	
	protected CheckedRegistry() {
		
	}
	
	static void init() {
		checkedMethods = MapFactory.create();
		
		I_Iterator it = ProxyCheckedInvoker.getPreInitInvokers();
		while (it.hasNext()) {
			ProxyCheckedInvoker pi = (ProxyCheckedInvoker) it.next();
			checkedMethods.put(pi.getName(), pi);
		}
	}
	
	static void uninit() {
		checkedMethods = null;
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
	I_CheckedInvoker getCheckedInvoker(String p) {
		I_CheckedInvoker toRet = null;
		if (checkedMethods == null) {
			toRet = ProxyCheckedInvoker.getInstance(p);
		} else {
			toRet = (I_CheckedInvoker) checkedMethods.get(p);
			if (toRet == null) {
				toRet = ProxyCheckedInvoker.getInstance(p);
				checkedMethods.put(p, toRet);
			} 
		}
		// not sure why this log message doesn't work from GWT???
		if (log.isDebugEnabled()) {
			log.debug("Returning " + toRet + " for key " + p);
			if (log.isTraceEnabled()) {
				Exception trace = new Exception("tracing lookup location");
				trace.fillInStackTrace();
				log.trace(trace.getMessage(), trace);
			}
		}
		return toRet;
	}
	
	/**
	 * new api for initalization
	 */
	void addCheckedInvokerDelegates(I_Map p) {
		if (checkedMethods == null) {
			init();
		}
		I_Iterator it = p.getIterator();
		while (it.hasNext()) {
			addCheckedInvokerDelegate(p, (String) it.next());
		}
	}
	
	void addCheckedInvokerDelegate(I_Map p, String key) {
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
	
	void replaceCheckedInvokerDelegates(I_Map p ) {
		if (log.isInfoEnabled()) {
			log.info("entering replaceCheckedInvokerDelegates...");
		}
		if (checkedMethods == null) {
			init();
		}
		I_Iterator it = p.getIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ProxyCheckedInvoker pi = (ProxyCheckedInvoker) checkedMethods.get(key);
			if (pi == null) {
					pi = new ProxyCheckedInvoker(key);
					pi.setDelegate((I_CheckedInvoker) p.get(key));
					checkedMethods.put(key, pi);
			} else {
				if (pi.getDelegate() == null) {
					addCheckedInvokerDelegate(p, key);
				} else {
					pi.setDelegate((I_CheckedInvoker) p.get(key));
				}
			}
			if (log.isDebugEnabled()) {
				log.info("replaceCheckedInvokerDelegates " + key + " is now " + pi);
			}
		}
		if (log.isInfoEnabled()) {
			log.info("exiting replaceInvokerDelegates...");
		}
	}
	
	static void clear() {
		if (checkedMethods != null) {
			checkedMethods.clear();
		}
		ProxyCheckedInvoker.clearPreInitInvokers();
	}
	
	void debug() {
		I_Iterator it2 = checkedMethods.keys();
		while (it2.hasNext()) {
			Object obj = it2.next();
			log.debug(obj);
		}
		log.debug("\n\n");
	}
}
