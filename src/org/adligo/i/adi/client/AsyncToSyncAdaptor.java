package org.adligo.i.adi.client;

import org.adligo.i.adi.client.I_Invoker;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * this is a single threaded way to make a async call synch
 * this is useful for developers who do NOT want to rewrite this 
 * all the time because everything in adi was done with the async api
 * to allow for GWT, and other optimized remote calls
 * 
 * @author scott
 *
 */
public class AsyncToSyncAdaptor implements AsyncCallback, Runnable {
	private Object result;
	private Throwable caught;
	private Object arg;
	private I_Invoker action;
	
	public void onFailure(Throwable caught) {
		this.caught = caught;
	}

	public void onSuccess(Object arg0) {
		this.result = arg0;
	}

	public Object getResult() {
		return result;
	}

	public Throwable getCaught() {
		return caught;
	}

	public Object getArg() {
		return arg;
	}

	public void setArg(Object arg) {
		this.arg = arg;
	}

	public I_Invoker getAction() {
		return action;
	}

	public void setAction(I_Invoker action) {
		this.action = action;
	}
	
	public void run() {
		if (action == null) {
			caught = new NullPointerException(getClass().getName()  + 
					" must have a action to run!");
			return;
		}
		action.invoke(arg, this);
	}

	public void dispose() {
		result = null;
		caught = null;
		arg = null;
		action = null;
	}
	
	/**
	 * this will make a synch call, 
	 * 
	 * DO NOT USE ON A GWT CLIENT UNLESS YOU HAVE A VERY SPECIAL REASON,
	 * IT WILL BE VERY SLOW
	 * 
	 * ONLY use when you know the action (I_Invoker) isLocal()
	 * and should process in the Current Thread, 
	 * otherwise use the Async API
	 * 
	 * @param action
	 * @param arg
	 * @return
	 * @throws Throwable
	 */
	public static final Object run(I_Invoker action, Object arg) throws Throwable {
		AsyncToSyncAdaptor adaptor = new AsyncToSyncAdaptor();
		adaptor.setAction(action);
		adaptor.setArg(arg);
		adaptor.run();
		
		Throwable t = adaptor.getCaught();
		if (t != null) {
			// Some Exception so throw it
			adaptor.dispose();
			throw t;
		}
		
		return adaptor.getResult();
	}
}
