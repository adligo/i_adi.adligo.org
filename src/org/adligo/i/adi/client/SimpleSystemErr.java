package org.adligo.i.adi.client;

public class SimpleSystemErr implements I_Invoker {

	protected static final SimpleSystemErr INSTANCE = new SimpleSystemErr();
	
	private SimpleSystemErr() {}
	
	public Object invoke(Object valueObject) {
		System.err.println(valueObject);
		return null;
	}

}
