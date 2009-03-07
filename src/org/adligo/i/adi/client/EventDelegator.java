package org.adligo.i.adi.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.ClassUtils;
import org.adligo.i.util.client.Event;
import org.adligo.i.util.client.I_Listener;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

/**
 * this is one way to pass all user events 
 * (user button clicks exc)
 * through a common location
 * 
 * delegation also provides a nice way to 
 * organize code
 * 
 * This is not in i_util simply so that
 * you have a logging to track 
 * what events are going through here
 * 
 * this can be used on J2EE, J2SE, J2ME and GWT
 * 
 * @author scott
 *
 */
public class EventDelegator implements I_Listener {
	private static final Log log = LogFactory.getLog(EventDelegator.class);
	
	private I_Map eventMap = MapFactory.create();
	
	/**
	 * @param source
	 * @param i
	 */
	public synchronized void addEventHandler(Object source, I_Listener i) {
		Object val = eventMap.get(source);
		if (val == null) {
			eventMap.put(source, i);
		} else if (ClassUtils.typeOf(val, ArrayList.class)) {
			ArrayList list = (ArrayList) val;
			list.add(i);
		} else {
			ArrayList list = new ArrayList();
			list.add(val);
			list.add(i);
			eventMap.put(source, list);
		}
	}
	
	public synchronized void removeEventHandler(Object source, I_Listener i) {
		Object val = eventMap.get(source);
		if (val == null) {
			// do nothing its not in there anyway
		} else if (ClassUtils.typeOf(val, ArrayList.class)) {
			ArrayList list = (ArrayList) val;
			list.remove(i);
		} else {
			eventMap.remove(source);
		}
		
	}

	public void onEvent(Event p) {
		Object destination = eventMap.get(p.getSource());
		if (log.isDebugEnabled()) {
			log.debug("enter onEvent " + p + " destination is " +
					destination);
		}
		
		if (destination == null) {
			throw new NullPointerException(
					"No listener found for Event " + p);
		} else if (ClassUtils.typeOf(destination, ArrayList.class)) {
			
			ArrayList list = (ArrayList) destination;
			if (log.isDebugEnabled()) {
				log.debug("onEvent with " + 
						list.size() + " listeners ");
			}
			Iterator it = list.iterator();
			
			boolean first = true;
			while (it.hasNext()) {
				I_Listener adaptor = (I_Listener) it.next();
				if (first) {
					send(p, adaptor);
					first = false;
				} else {
					send(p.copy(), adaptor);
				}
			}
		} else {
			send(p, (I_Listener) destination);
		}
	}

	private void send(Event p, I_Listener adaptor) {
		if (log.isDebugEnabled()) {
			log.debug("sending event " + p + " to " + adaptor);
		}
		adaptor.onEvent(p);
	}
}
