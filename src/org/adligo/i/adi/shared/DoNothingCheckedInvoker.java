package org.adligo.i.adi.shared;


public class DoNothingCheckedInvoker implements I_CheckedInvoker {

	//do nothing
	public Object invoke(Object valueObject) throws InvocationException {
		return valueObject;
	}

}
