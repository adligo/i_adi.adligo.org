package org.adligo.i.adi.client;

public class SimpleSystemOut implements I_Invoker {
	protected static final SimpleSystemOut INSTANCE = new SimpleSystemOut();
	
	private SimpleSystemOut() {}
	
	public Object invoke(Object valueObject) {
		System.out.println(valueObject);
		return null;
	}

}
