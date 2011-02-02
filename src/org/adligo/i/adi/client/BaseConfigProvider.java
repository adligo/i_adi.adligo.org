package org.adligo.i.adi.client;

import org.adligo.i.adi.client.models.ConfigRequest;


public class BaseConfigProvider implements I_Invoker {
	
	public Object invoke(Object valueObject) {
		try {
			return ((ConfigRequest) valueObject).getDefaultSetting();
		} catch (ClassCastException x) {
			IllegalArgumentException e = new IllegalArgumentException(
					this.getClass().getName() + " takes a " +
					ConfigRequest.class.getName() + 
					" and you passed it a " + valueObject);
			throw e;
		}
	}

}
