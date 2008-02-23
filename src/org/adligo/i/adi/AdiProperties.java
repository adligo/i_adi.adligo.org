package org.adligo.i.adi;

import java.util.Hashtable;
import java.io.IOException;
import org.adligo.i.log.LoadProperties;
import org.adligo.i.log.Log;
import org.adligo.i.log.LogFactory;

public class AdiProperties {
	public static final String REGISTRY_LOOKUP = "registry_lookup";
	public static final String REGISTRY_CLASS = "registry_class";
	private static final Log log = LogFactory.getLog(AdiProperties.class);
	
	protected static final AdiProperties instance = getProps();
	private I_Registry reg;
	
	private static AdiProperties getProps() {
		Hashtable items = new Hashtable();
		try {
			items = LoadProperties.loadProperties("/adi.properties", false);
			return new AdiProperties(items);
		} catch (IOException x) {
			return null;
		}
	}

	private AdiProperties(Hashtable p) {
		String lookupName = (String) p.get(REGISTRY_LOOKUP);
		if (lookupName != null) {
			if (log.isDebugEnabled()) {
				log.debug("registry lookup is " + lookupName);
			}
			try {
				Class clazz = Class.forName(lookupName);
				I_RegistryLookup lookup = (I_RegistryLookup) clazz.newInstance();
				reg = lookup.lookup();
				if (log.isDebugEnabled()) {
					log.debug("registry is a " + reg);
				}
				return;
			} catch (Exception x) {
				log.error(x.getMessage(), x);
			}
		}
		String regClass = (String) p.get(REGISTRY_CLASS);
		if (log.isDebugEnabled()) {
			log.debug("registry class is " + regClass);
		}
		if (regClass != null) {
			try {
				reg = RegistryCreator.createRegistryFromClassName(regClass);
			} catch (Exception x) {
				x.printStackTrace();
				throw new RuntimeException("Couldn't create registry class " + regClass);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("registry is a " + reg);
		}
	}
	
	public I_Registry getRegistry() {
		return reg;
	}
	
}
