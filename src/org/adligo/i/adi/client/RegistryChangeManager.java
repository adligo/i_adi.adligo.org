package org.adligo.i.adi.client;

import org.adligo.i.util.client.CollectionFactory;
import org.adligo.i.util.client.I_Collection;
import org.adligo.i.util.client.I_Iterator;

public class RegistryChangeManager {
	private I_Collection listeners = CollectionFactory.create();
	private I_Registry lastRegistry = null;
	
	public void addListener(I_RegistryChangeListener p) {
		if (p != null) {
			
			listeners.add(p);
			if (lastRegistry != null) {
				p.registryChanged(lastRegistry);
			}
				
		}
	}
	
	public void removeListener(I_RegistryChangeListener p) {
		if (p != null) {
			listeners.add(p);
		}
	}
	
	public void notifyRegistryChanged(I_Registry p) {
		
		I_Iterator it = listeners.getIterator();
		
		while (it.hasNext()) {
			I_RegistryChangeListener listener = (I_RegistryChangeListener) it.next();
			listener.registryChanged(p);
		}
		lastRegistry = p;
	}
	
	
}
