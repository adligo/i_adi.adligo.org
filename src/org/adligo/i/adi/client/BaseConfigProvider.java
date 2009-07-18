package org.adligo.i.adi.client;


public class BaseConfigProvider implements I_Invoker {
	
	public Object invoke(Object valueObject) {
		if (valueObject instanceof ConfigRequest) {
			return ((ConfigRequest) valueObject).getDefaultSetting();
		} else {
			RuntimeException e = new RuntimeException(
					this.getClass().getName() + " takes a " +
					ConfigRequest.class.getName() + 
					" and you passed it a " + valueObject);
			throw e;
		}
	}

}
