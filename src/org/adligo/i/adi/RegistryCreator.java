package org.adligo.i.adi;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegistryCreator {
	private static final Log log = LogFactory.getLog(RegistryCreator.class);
	
	protected static I_Registry instance = createInstance();
	
	/**
	 * @return
	 */
	protected static I_Registry createInstance() {
		if (log.isDebugEnabled()) {
			log.debug("entering");
		}
		if (instance != null) {
			return instance;
		}
		I_Registry toRet = null;
		if (log.isDebugEnabled()) {
			log.debug("attempting init");
		}
		
		Exception e = null;
		
		try {
			String clazzName = System.getProperty(Registry.IMPLEMTAION_CLASS);
			toRet = createRegistryFromClassName(toRet, clazzName);
		} catch (Exception x) {
			log.error(x.getMessage(), x);
			e = x;
		}
		if (log.isDebugEnabled()) {
			log.debug("found " + toRet + " from System property " +
					Registry.IMPLEMTAION_CLASS);
		}
		if (toRet == null) {
			try {
				/*
				 * j2ME doesn't have javax naming packages 
				 */
				if (null == System.getProperty("microedition.configuration")) {
					javax.naming.InitialContext context = new javax.naming.InitialContext();
					toRet = (I_Registry) context.lookup(
							Registry.IMPLEMTAION_CLASS);
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
			//prevent multiple calls since,
			// the system is basicly down at this point
			RuntimeException ruin = null;
			if (e != null) {
				ruin = new RuntimeException(e);
			} else {
				ruin = new RuntimeException("Could not locate a implementation, try ... \n" +
						" setting this up through jndi or System.setProperty(Registry.IMPLEMTAION_CLASS, \n " +
						" \t\t\"org.adligo.examples.i.adsi.ExampleMethodRegistry\"); ");
			}
			
			throw new RuntimeException(e);
		}
		return toRet;
	}
	
	/**
	 * read a properties file when jndi isn't available
	 * @return
	 * @throws Exception
	 */
	private static String getClassNameFromPropertyFile() throws Exception {
		Properties props  = new Properties();
		File file = new File("adi.properties");
		FileInputStream in = new FileInputStream(file);
		in.close();
		return props.getProperty(Registry.IMPLEMTAION_CLASS);
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
	private static I_Registry createRegistryFromClassName(I_Registry toRet,
			String clazzName) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
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
