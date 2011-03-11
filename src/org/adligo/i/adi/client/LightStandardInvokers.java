package org.adligo.i.adi.client;

import org.adligo.i.adi.client.light.CacheReader;
import org.adligo.i.adi.client.light.CacheRemover;
import org.adligo.i.adi.client.light.CacheWriter;
import org.adligo.i.adi.client.light.MemoryReader;
import org.adligo.i.adi.client.light.MemoryWriter;

import com.google.gwt.junit.Platform;

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
