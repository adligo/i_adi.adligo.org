package org.adligo.i.adi.shared;

public class SimpleSystemOut implements I_Invoker {
	public static final SimpleSystemOut INSTANCE = new SimpleSystemOut();
	
	private SimpleSystemOut() {}
	
	public Object invoke(Object valueObject) {
		System.out.println(valueObject);
		return null;
	}

}
