package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.CollectionFactory;
import org.adligo.i.util.client.I_Collection;
import org.adligo.i.util.client.I_Iterator;

public class CheckedInvokerList implements I_CheckedInvoker {
	private static final Log log = LogFactory.getLog(CheckedInvokerList.class);
	private I_Collection delegates = CollectionFactory.create();
	
	
	public Object invoke(Object valueObject) throws InvocationException {
		if (log.isInfoEnabled()) {
			log.info("in invoke " + valueObject);
		}
		
		Object toRet = null;
		I_Iterator it = getDelegates();
		while (it.hasNext()) {
			I_Invoker in = (I_Invoker) it.next();
			toRet = in.invoke(valueObject);
			if (log.isDebugEnabled()) {
				log.debug(" call to " + in + " with " + valueObject + 
						" returned " + toRet);
			}
		}
		return toRet;
	}
	
	public I_Iterator getDelegates() {
		return delegates.getIterator();
	}

	public synchronized void add(I_CheckedInvoker delegate) {
		delegates.add(delegate);
	}
	
	public synchronized void remove(I_CheckedInvoker delegate) {
		delegates.remove(delegate);
	}
}
