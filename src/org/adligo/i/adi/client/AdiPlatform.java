package org.adligo.i.adi.client;

import org.adligo.i.log.client.LogFactory;
import org.adligo.i.log.client.LogPlatform;
import org.adligo.i.util.client.Event;
import org.adligo.i.util.client.I_Listener;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.Platform;
import org.adligo.i.util.client.PropertyFactory;

public class AdiPlatform {
	private static I_Registry reg = null;
	
	public static synchronized  final void init(I_Registry p) {
		reg = p;
		if (reg == null) {
			throw new NullPointerException("The registry can't be null!");
		}
	}
	
	public static I_Registry getRegistry() {
		return reg;
	}
	
	protected static boolean isLogEnabled() {
		return false;
	}
	
	protected static void log(String p){
		System.out.println(p);
	}
}
