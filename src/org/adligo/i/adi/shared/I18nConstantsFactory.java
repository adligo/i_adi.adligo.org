package org.adligo.i.adi.shared;

import org.adligo.i.util.shared.I_Map;
import org.adligo.i.util.shared.MapFactory;

public class I18nConstantsFactory implements I_Invoker {
	public static final I18nConstantsFactory INSTANCE = new I18nConstantsFactory();
	
	private I_Map map;
	/**
	 * the default factory provides a fail over
	 * for classes that actually implement the GWT constants class directly
	 * (unlike the models_core package which is meant to be used on j2me 
	 * as well as gwt and j2se)
	 * 
	 */
	private I_Invoker defalutFactory;
	
	private I18nConstantsFactory() {}
	
	public Object invoke(Object valueObject) {
		Object val = getMap().get(valueObject);
		try {
			return ((I_Invoker) val).invoke(valueObject);
		} catch (ClassCastException x) {
			if (val == null && defalutFactory != null) {
				val = defalutFactory.invoke(valueObject);
			}
		}
		return val;
	}
	
	
	/**
	 * lazy init the map since
	 * MapFactory will not be available at 
	 * Registry construction time
	 * 
	 * @return
	 */
	private I_Map getMap() {
		if (map == null) {
			map = MapFactory.create();
		}
		return map;
	}

	/**
	 * J2me doesn't have annotations or Generics
	 * @param clazz
	 * @param impl
	 */
	public synchronized void put(Class clazz, Object impl) {
		getMap().put(clazz, impl);
	}

	public synchronized I_Invoker getDefalutFactory() {
		return defalutFactory;
	}

	public synchronized void setDefalutFactory(I_Invoker defalutFactory) {
		this.defalutFactory = defalutFactory;
	}
}
