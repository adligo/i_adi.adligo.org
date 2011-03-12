package org.adligo.i.adi.client.light;

import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.ProxyInvoker;
import org.adligo.i.adi.client.Registry;
import org.adligo.i.adi.client.StandardInvokers;

/**
 * the light standard invokers are lightweight
 * cache and memory implementations which are optimized for 
 * lightweight clients (GWT and JME) 
 * by using a single map instead of a map of maps like the heavy ones do
 * @author scott
 *
 */
public class LightStandardInvokers {
	private static final ProxyInvoker CACHE_READER = 
		new ProxyInvoker(InvokerNames.CACHE_READER, CacheReader.INSTANCE);
	private static final ProxyInvoker CACHE_WRITER = 
		new ProxyInvoker(InvokerNames.CACHE_WRITER, CacheWriter.INSTANCE);
	private static final ProxyInvoker CACHE_REMOVER = 
		new ProxyInvoker(InvokerNames.CACHE_REMOVER, CacheRemover.INSTANCE);

	private static final ProxyInvoker MEMORY_READER = 
		new ProxyInvoker(InvokerNames.MEMORY_READER, MemoryReader.INSTANCE);
	private static final ProxyInvoker MEMORY_WRITER = 
		new ProxyInvoker(InvokerNames.MEMORY_WRITER, MemoryWriter.INSTANCE);
	
	/**
	 * this method is not to be used other than for junit tests
	 * and internal adi package code
	 * @param name
	 * @return
	 */
	public static final ProxyInvoker get(String name) {
		if (InvokerNames.CACHE_READER.equals(name)) {
			return CACHE_READER;
		}
		if (InvokerNames.CACHE_WRITER.equals(name)) {
			return CACHE_WRITER;
		}
		if (InvokerNames.CACHE_REMOVER.equals(name)) {
			return CACHE_REMOVER;
		}
		if (InvokerNames.MEMORY_READER.equals(name)) {
			return MEMORY_READER;
		}
		if (InvokerNames.MEMORY_WRITER.equals(name)) {
			return MEMORY_WRITER;
		}
		return null;
	}
	
	/**
	 * this method should be used when initalizeing 
	 * a light weight JSE command line or small applet
	 * that doesn't want the heavy Cache and Memory impl
	 * before locking it.
	 *   It also locks all the standard invokers
	 */
	public void useLightWeightInvokers() {
		Registry.addOrReplaceInvoker(
				InvokerNames.CACHE_READER, CacheReader.INSTANCE);
		Registry.addOrReplaceInvoker(
				InvokerNames.CACHE_WRITER, CacheWriter.INSTANCE);
		Registry.addOrReplaceInvoker(
				InvokerNames.CACHE_REMOVER, CacheRemover.INSTANCE);
		
		Registry.addOrReplaceInvoker(
				InvokerNames.MEMORY_READER, MemoryReader.INSTANCE);
		Registry.addOrReplaceInvoker(
				InvokerNames.MEMORY_WRITER, MemoryReader.INSTANCE);
		
		StandardInvokers.lockStandardInvokers();
		
	}

}
