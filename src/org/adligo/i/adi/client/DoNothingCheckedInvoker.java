package org.adligo.i.adi.client;


public class DoNothingCheckedInvoker implements I_CheckedInvoker {

	//do nothing
	public Object invoke(Object valueObject) throws InvocationException {
		return valueObject;
	}

}
