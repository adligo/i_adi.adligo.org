package org.adligo.i.adi.shared;

import org.adligo.i.adi.shared.heavy.HeavyStandardInvokers;
import org.adligo.i.adi.shared.light.CacheReader;
import org.adligo.i.adi.shared.light.CacheRemover;
import org.adligo.i.adi.shared.light.CacheWriter;
import org.adligo.i.adi.shared.light.LightStandardInvokers;
import org.adligo.i.adi.shared.light.MemoryReader;
import org.adligo.i.adi.shared.light.MemoryWriter;
import org.adligo.i.util.shared.Platform;

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
	
	/**
	 * this method should be called to lock 
	 * all of the standard invokers
	 */
	public static void lockStandardInvokers() {
		Registry.lockInvoker(InvokerNames.CACHE_READER);
		Registry.lockInvoker(InvokerNames.CACHE_WRITER);
		Registry.lockInvoker(InvokerNames.CACHE_REMOVER);
		Registry.lockInvoker(InvokerNames.MEMORY_READER);
		Registry.lockInvoker(InvokerNames.MEMORY_WRITER);
		
		Registry.lockInvoker(InvokerNames.CLOCK);
		Registry.lockInvoker(InvokerNames.CONFIGURATION_PROVIDER);
		Registry.lockInvoker(InvokerNames.CONSTANTS_FACTORY);
	}
}
