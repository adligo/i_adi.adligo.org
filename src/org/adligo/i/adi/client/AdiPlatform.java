package org.adligo.i.adi.client;

import org.adligo.i.log.client.LogFactory;
import org.adligo.i.log.client.LogPlatform;
import org.adligo.i.util.client.Event;
import org.adligo.i.util.client.I_Listener;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.Platform;
import org.adligo.i.util.client.PropertyFactory;

public class AdiPlatform {
	private static I_Registry reg = null;
	private static RegistryChangeManager manager = new RegistryChangeManager();
	
	public static synchronized final void init(I_Registry p) {
		if (reg == null) {
			reg = p;
			if (reg == null) {
				throw new NullPointerException("The registry can't be null!");
			}
			// only your registry can change itself after this point
			//calling the Registry (super.changed()
			// which is only necessary when the adaptor chain
			// has been modified before the first proxy (mediator) exc
			manager.notifyRegistryChanged(reg);
		}
	}
	
	public static I_Registry getRegistry() {
		return reg;
	}
	
	public static Object addListener(I_RegistryChangeListener p) {
		manager.addListener(p);
		return Boolean.TRUE;
	}
	
	public static Object removeListener(I_RegistryChangeListener p) {
		manager.removeListener(p);
		return Boolean.TRUE;
	}
	
	protected static boolean isLogEnabled() {
		return false;
	}
	
	protected static void changed(I_Registry p) {
		manager.notifyRegistryChanged(p);
	}
}
