package org.adligo.i.adi;

import org.adligo.i.log.Log;
import org.adligo.i.log.LogFactory;


public class RegistryCreator {
	private static Log log =  LogFactory.getLog(RegistryCreator.class);
	protected static I_Registry instance = createInstance();
	
	/**
	 * @return
	 */
	protected static I_Registry createInstance() {
		I_Registry toRet = null;
		if (log.isDebugEnabled()) {
			log.debug("attempting init");
		}
		
		toRet = AdiProperties.instance.getRegistry();
		
		Exception e = null;
		if (toRet == null) {
			
			
			try {
				String clazzName = System.getProperty(Registry.IMPLEMTAION_CLASS);
				toRet = createRegistryFromClassName(clazzName);
			} catch (Exception x) {
				log.error(x.getMessage(), x);
				e = x;
			}
			if (log.isDebugEnabled()) {
				log.debug("found " + toRet + " from System property " +
						Registry.IMPLEMTAION_CLASS);
			}
		}
		
		if (toRet == null) {
			try {
				// j2ME doesn't have javax naming packages 
				if (null == System.getProperty("microedition.configuration")) {
					/*
					javax.naming.InitialContext context = new javax.naming.InitialContext();
					toRet = (I_Registry) context.lookup(
							Registry.IMPLEMTAION_CLASS);
							*/
							
				}
			} catch (Exception x) {
				log.error(x.getMessage(), x);
				e = x;
			}
			if (log.isDebugEnabled()) {
				log.debug("found " + toRet + " from jndi for name " +
						Registry.IMPLEMTAION_CLASS);
			}
		}
		
		if (toRet == null) {
			throw new RuntimeException("Could not locate a implementation, try ... \n" +
						" setting this up through jndi or System.setProperty(Registry.IMPLEMTAION_CLASS, \n " +
						" \t\t\"org.adligo.examples.i.adsi.ExampleMethodRegistry\"); \n" +
						"or through the adi.properties file registry_class=someClass");
		}
		return toRet;
	}
	
	/**
	 * Do a class.ForName and create a instance
	 * @param toRet
	 * @param clazzName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected static I_Registry createRegistryFromClassName(String clazzName) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		
		I_Registry toRet = null;
		if (clazzName != null) {
			
			if (log.isDebugEnabled()) {
				log.debug("attempting instaniate " + clazzName);
			}
			Class clazz = Class.forName(clazzName);
			if (log.isDebugEnabled()) {
				log.debug("got clazz " + clazz);
			}
			toRet = (I_Registry) clazz.newInstance();
			if (log.isDebugEnabled()) {
				log.debug("got instance " + toRet);
			}
		}
		return toRet;
	}

}
