package org.adligo.i.adi.shared;

public class SimpleSystemErr implements I_Invoker {

	public static final SimpleSystemErr INSTANCE = new SimpleSystemErr();
	
	private SimpleSystemErr() {}
	
	public Object invoke(Object valueObject) {
		System.err.println(valueObject);
		return null;
	}

}
