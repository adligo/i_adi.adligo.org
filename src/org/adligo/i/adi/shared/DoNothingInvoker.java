package org.adligo.i.adi.shared;

public class DoNothingInvoker implements I_Invoker {

	//do nothing
	public Object invoke(Object valueObject) {
		return valueObject;
	}

}
