package org.adligo.i.adi.client;

public class StandardInvokers {

	private static final ProxyInvoker CACHE_READER = 
		new ProxyInvoker(InvokerNames.CACHE_READER, new CacheReader());
	private static final ProxyInvoker CACHE_WRITER = 
		new ProxyInvoker(InvokerNames.CACHE_WRITER, new CacheWriter());
	private static final ProxyInvoker CACHE_REMOVER = 
		new ProxyInvoker(InvokerNames.CACHE_REMOVER, new CacheRemover());
	private static final ProxyInvoker CONFIG_PROVIDER = 
		new ProxyInvoker(InvokerNames.CONFIGURATION_PROVIDER,
				new BaseConfigProvider());
	private static final ProxyInvoker OUT = 
		   new ProxyInvoker(InvokerNames.OUT, new SimpleSystemOut());
	private static final ProxyInvoker CLOCK = 
		   new ProxyInvoker(InvokerNames.CLOCK, new SimpleClock());
	
	/**
	 * can't even be done with a I_Map due to init issues
	 * @param name
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
		if (InvokerNames.CONFIGURATION_PROVIDER.equals(name)) {
			return CONFIG_PROVIDER;
		}
		if (InvokerNames.CLOCK.equals(name)) {
			return CLOCK;
		}
		if (InvokerNames.OUT.equals(name)) {
			return OUT;
		}
		return null;
	}
}
