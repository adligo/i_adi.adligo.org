package org.adligo.i.adi.shared;

import org.adligo.i.adi.shared.models.ConfigRequest;


public class BaseConfigProvider implements I_Invoker {
	
	public static final String AND_WAS_PASSED = " and was passed ";
	public static final String TAKES_A = " takes a ";

	public Object invoke(Object valueObject) {
		try {
			return ((ConfigRequest) valueObject).getDefaultSetting();
		} catch (ClassCastException x) {
			IllegalArgumentException e = new IllegalArgumentException(
					this.getClass().getName() + TAKES_A +
					ConfigRequest.class.getName() + 
					AND_WAS_PASSED + valueObject);
			throw e;
		}
	}

}
