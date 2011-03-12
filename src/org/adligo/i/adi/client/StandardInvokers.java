package org.adligo.i.adi.client;

import org.adligo.i.adi.client.light.CacheReader;
import org.adligo.i.adi.client.light.CacheRemover;
import org.adligo.i.adi.client.light.CacheWriter;
import org.adligo.i.adi.client.light.MemoryReader;
import org.adligo.i.adi.client.light.MemoryWriter;
import org.adligo.i.util.client.Platform;

public class StandardInvokers {
	private static final ProxyInvoker CLOCK = 
		   new ProxyInvoker(InvokerNames.CLOCK, SimpleClock.INSTANCE);
	
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
		if (Platform.getPlatform() == Platform.JSE) {
			ProxyInvoker toRet = HeavyStandardInvokers.get(name);
			if (toRet != null) {
				return toRet;
			}
		} else {
			ProxyInvoker toRet = LightStandardInvokers.get(name);
			if (toRet != null) {
				return toRet;
			}
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
