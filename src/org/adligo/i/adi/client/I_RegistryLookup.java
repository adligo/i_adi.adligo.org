package org.adligo.i.adi.client;

/**
 * JNDI isn't avaliable in CLDC so this is a way to wrap a plugin
 * so that CLDC compliant code may also run in a full blown J2EE
 * server with out any problems
 * 
 * @author scott
 *
 */
public interface I_RegistryLookup {
	public I_Registry lookup();
}
