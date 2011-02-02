package org.adligo.i.adi.client;

import java.security.InvalidParameterException;

import org.adligo.i.adi.client.models.ConfigRequest;


public class BaseConfigProvider implements I_Invoker {
	
	public Object invoke(Object valueObject) {
		try {
			return ((ConfigRequest) valueObject).getDefaultSetting();
		} catch (ClassCastException x) {
			InvalidParameterException e = new InvalidParameterException(
					this.getClass().getName() + " takes a " +
					ConfigRequest.class.getName() + 
					" and you passed it a " + valueObject);
			throw e;
		}
	}

}
