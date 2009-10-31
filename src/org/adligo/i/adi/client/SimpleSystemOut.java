package org.adligo.i.adi.client;

public class SimpleSystemOut implements I_Invoker {

	public Object invoke(Object valueObject) {
		System.out.println(valueObject);
		return null;
	}

}
