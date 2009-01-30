package org.adligo.i.adi.client;

public interface I_Invoker extends I_HandlerAsync {
	/**
	 * this should return false if the invoker invokes a procedure on 
	 * a remote machine (the way GWT RCP is tipically used
	 * 
	 * This is mostly just a convenience method 
	 * to help with the adligo gwt_util's RpcProxy setup
	 * since I am using this to wrap local code as well
	 * 
	 * may be useful for j2me automated setup or 
	 * j2se rmi automated setup
	 * 
	 * @return
	 */
	public boolean isLocal();
}
