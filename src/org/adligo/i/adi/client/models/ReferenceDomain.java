package org.adligo.i.adi.client.models;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.ArrayIterator;
import org.adligo.i.util.client.I_Iterator;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

/**
 *
 * this allows for faster cache and memory writing by
 * narrowing the scope of the synchronization of writes down.
 * Currently all writing to both is synchronized 
 * by the backing collection
 * of the I_Map impl (@see I_Map). 
 * (java.util.concurrent.ConcurrentHashMap
 * (syncronized writes, unsyncronized reads) on jse 
 * Hashtable (fully synchronized) on jme, 
 * HashMap on GWT (single threaded, no synchronization).
 * 
 * @author scott
 *
 */
public class ReferenceDomain {
	private static final Log log = LogFactory.getLog(ReferenceDomain.class);
	private I_Map map = MapFactory.createSync();

	public Object get(Object name) {
		if (name == null) {
			return null;
		}
		return get(new ReferenceAddressName(name.toString()));
	}
	
	public Object get(String name) {
		if (name == null) {
			return null;
		}
		return get(new ReferenceAddressName(name));
	}
	
	public Object get(ReferenceAddressName name) {
		if (name == null) {
			return null;
		}
		String parentFullPath = name.getParentFullPath();
		I_Map childMap = (I_Map) map.get(parentFullPath);
		if (childMap == null) {
			return null;
		}
		return childMap.get(name.getLocalPath());
	}
	
	public void put(Object name, Object p) {
		if (p == null || name == null) {
			return;
		}
		put(new ReferenceAddressName(name.toString()), p);
	}
	
	public void put(String name, Object p) {
		if (p == null || name == null) {
			return;
		}
		put(new ReferenceAddressName(name), p);
	}
	
	/**
	 * see createChildMap comments
	 * @param name
	 * @param p
	 */
	public void put(ReferenceAddressName name, Object p) {
		if (p == null || name == null) {
			return;
		}
		String parentFullPath = name.getParentFullPath();
		//see createChildMap java doc comments
		I_Map childMap = (I_Map) map.get(parentFullPath);
		if (childMap == null) {
			childMap = createChildMap(parentFullPath);
		}
		childMap.put(name.getLocalPath(), p);
	}
	
	/**
	 * At first glance this may appear to be a double checked locking pattern
	 * however it is just different enough that it should always work correctly.
	 * 
	 * The difference is that the null check is compairing a value that has been put 
	 * into a synchronized map (java.util.concurrent.ConcurrentHashMap on JSE
	 * or Hashtable on JME) which provides another layer of synchronization.
	 * This means that the object (in this case another I_Map 
	 * java.util.concurrent.ConcurrentHashMap on JSE or Hashtable on JME)
	 * would have been fully initalized before being placed in the parent 
	 * map member instance variable.
	 */
	private synchronized I_Map createChildMap(String parentFullPath) {
		I_Map childMap = (I_Map) map.get(parentFullPath);
		if (childMap == null) {
			childMap = MapFactory.createSync();
			map.put(parentFullPath, childMap);
		}
		return childMap;
	}
	
	public void remove(Object name) {
		if (name == null) {
			return;
		}
		remove(new ReferenceAddressName(name.toString()));
	}
	
	public void remove(String name) {
		if (name == null) {
			return;
		}
		remove(new ReferenceAddressName(name));
	}
	
	public void remove(ReferenceAddressName name) {
		if (name == null) {
			return;
		}
		String parentFullPath = name.getParentFullPath();
		I_Map childMap = (I_Map) map.get(parentFullPath);
		if (childMap != null) {
			childMap.remove(name.getLocalPath());
			if (childMap.size() == 0) {
				removeChildMap(parentFullPath, childMap);
			}
		}
	}
	
	/**
	 * see comments on createChildMap
	 * @param parentFullPath
	 * @param childMap
	 */
	private synchronized void removeChildMap(String parentFullPath, I_Map childMap) {
		if (childMap.size() == 0) {
			map.remove(parentFullPath);
		}
	}
	
	/**
	 * special api for the time index
	 * to remove a whole chunck at once
	 * @param parentFullPath
	 */
	public synchronized void removeChildMap(String parentFullPath) {
		map.remove(parentFullPath);
	}
	
	public I_Iterator topKeys() {
		return map.getKeysIterator();
	}
	
	public I_Iterator subKeys(String p) {
		I_Map local = (I_Map) map.get(p);
		if (local == null) {
			return new ArrayIterator(new Object[]{});
		}
		return local.getKeysIterator();
	}
	
	public I_Iterator subValues(String p) {
		I_Map local = (I_Map) map.get(p);
		if (local == null) {
			return new ArrayIterator(new Object[]{});
		}
		return local.getValuesIterator();
	}
	
	public int size() {
		int size = 0;
		I_Iterator it = map.getValuesIterator();
		while (it.hasNext()) {
			I_Map item = (I_Map) it.next();
			size = size + item.size();
		}
		return size;
	}
}
