package org.adligo.i.adi.client.heavy;

import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.ProxyInvoker;

/**
 * the heavy standard invokers are optimized for JEE (Java Enterprise Edition)
 * they use a map of maps to narrow the scope of synchronization
 * for faster writing to cache or memory
 * 
 * They can also be used on JSE (Java Standard Edition)
 * applications (swing,awt,swt) if your using a large 
 * JSE application.  If your writing a small
 * jse application (small applet or command line program)
 * you may want to switch these invokers to the lightweight ones.
 * 
 * @author scott
 *
 */
public class HeavyStandardInvokers {
	private static final ProxyInvoker CACHE_READER = 
		new ProxyInvoker(InvokerNames.CACHE_READER, HeavyCacheReader.INSTANCE);
	private static final ProxyInvoker CACHE_WRITER = 
		new ProxyInvoker(InvokerNames.CACHE_WRITER, HeavyCacheWriter.INSTANCE);
	private static final ProxyInvoker CACHE_REMOVER = 
		new ProxyInvoker(InvokerNames.CACHE_REMOVER, HeavyCacheRemover.INSTANCE);

	private static final ProxyInvoker MEMORY_READER = 
		new ProxyInvoker(InvokerNames.MEMORY_READER, HeavyMemoryReader.INSTANCE);
	private static final ProxyInvoker MEMORY_WRITER = 
		new ProxyInvoker(InvokerNames.MEMORY_WRITER, HeavyMemoryWriter.INSTANCE);
	
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
}
