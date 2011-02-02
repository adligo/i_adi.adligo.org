package org.adligo.i.adi.client;

public class StandardInvokers {
	private static final ProxyInvoker CLOCK = 
		   new ProxyInvoker(InvokerNames.CLOCK, SimpleClock.INSTANCE);
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
	
	private static final ProxyInvoker CONFIG_PROVIDER = 
		new ProxyInvoker(InvokerNames.CONFIGURATION_PROVIDER,
				new BaseConfigProvider());
	private static final ProxyInvoker OUT = 
		   new ProxyInvoker(InvokerNames.OUT, SimpleSystemOut.INSTANCE);
	private static final ProxyInvoker ERR = 
		   new ProxyInvoker(InvokerNames.ERR, SimpleSystemErr.INSTANCE);

	private static final ProxyInvoker I18N_CONSTANTS_FACTORY = 
			new ProxyInvoker(InvokerNames.CONSTANTS_FACTORY, I18nConstantsFactory.INSTANCE);
	
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
		if (InvokerNames.MEMORY_READER.equals(name)) {
			return MEMORY_READER;
		}
		if (InvokerNames.MEMORY_WRITER.equals(name)) {
			return MEMORY_WRITER;
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
		if (InvokerNames.ERR.equals(name)) {
			return ERR;
		}
		if (InvokerNames.CONSTANTS_FACTORY.equals(name)) {
			return I18N_CONSTANTS_FACTORY;
		}
		return null;
	}
}
