package org.adligo.i.adi.client;

public class SimpleSystemErr implements I_Invoker {

	public Object invoke(Object valueObject) {
		System.err.println(valueObject);
		return null;
	}

}
