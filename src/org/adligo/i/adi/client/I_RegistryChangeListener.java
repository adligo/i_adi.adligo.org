package org.adligo.i.adi.client;

public interface I_RegistryChangeListener {
	/**
	 * 
	 * This is so that your code can dynamicly reconfigure itself
	 * depending on notification that something changed
	 * 
	 * (like your sysadmin pressed the reload config button!)
	 * 
	 * @param p
	 */
	public void registryChanged(I_Registry p);
}
