package org.adligo.i.adi.client;

public class DoNothingInvoker implements I_Invoker {

	//do nothing
	public Object invoke(Object valueObject) {
		return valueObject;
	}

}
