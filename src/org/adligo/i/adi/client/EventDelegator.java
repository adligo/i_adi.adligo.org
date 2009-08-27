package org.adligo.i.adi.client;


import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.ArrayCollection;
import org.adligo.i.util.client.ClassUtils;
import org.adligo.i.util.client.Event;
import org.adligo.i.util.client.I_Iterator;
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
	private String name = "";
	
	public EventDelegator() {}
	
	public EventDelegator(String p_name) {
		name = p_name;
	}
	/**
	 * @param source
	 * @param i
	 */
	public synchronized void addEventHandler(Object source, I_Listener i) {
		Object val = eventMap.get(source);
		if (val == null) {
			eventMap.put(source, i);
		} else if (ClassUtils.typeOf(val, ArrayCollection.class)) {
			ArrayCollection list = (ArrayCollection) val;
			list.add(i);
		} else {
			ArrayCollection list = new ArrayCollection();
			list.add(val);
			list.add(i);
			eventMap.put(source, list);
		}
	}
	
	public synchronized void removeEventHandler(Object source, I_Listener i) {
		Object val = eventMap.get(source);
		if (val == null) {
			// do nothing its not in there anyway
		} else if (ClassUtils.typeOf(val, ArrayCollection.class)) {
			ArrayCollection list = (ArrayCollection) val;
			list.remove(i);
		} else {
			eventMap.remove(source);
		}
		
	}

	public void onEvent(Event p) {
		try {
			Object destination = eventMap.get(p.getSource());
			if (log.isDebugEnabled()) {
				log.debug(name + " enter onEvent " + p + "\n destination is " +
						destination);
			}
			
			if (destination == null) {
				throw new NullPointerException(
						name + " No listener found for Event " + p);
			} else if (ClassUtils.typeOf(destination, ArrayCollection.class)) {
				
				ArrayCollection list = (ArrayCollection) destination;
				if (log.isDebugEnabled()) {
					log.debug( name + " onEvent with " + 
							list.size() + " listeners ");
				}
				I_Iterator it = list.getIterator();
				
				boolean first = true;
				int counter = 0;
				while (it.hasNext()) {
					I_Listener listener = (I_Listener) it.next();
					if (first) {
						send(p, listener, counter);
						first = false;
					} else {
						send(p.copy(), listener, counter);
					}
					counter++;
				}
			} else {
				send(p, (I_Listener) destination, 0);
			}
		} catch (Exception x) {
			log.error(x.getMessage(), x);
		}
	}

	private void send(Event p, I_Listener listener, int counter) {
		if (log.isDebugEnabled()) {
			log.debug(name + " sending event " + p + "\n to listener " + 
					counter + "," + listener);
		}
		listener.onEvent(p);
	}
}
