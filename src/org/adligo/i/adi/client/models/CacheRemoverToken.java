package org.adligo.i.adi.client.models;

import org.adligo.i.util.client.CollectionFactory;
import org.adligo.i.util.client.I_Collection;
import org.adligo.i.util.client.I_Iterator;

public class CacheRemoverToken {
	public static final short SWEEP_ALL_TYPE = 0;
	public static final short REMOVE_LIST_TYPE =1;
	
	private short type = REMOVE_LIST_TYPE;
	private I_Collection keys = CollectionFactory.create();
	
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	
	public I_Iterator getKeys() {
		return keys.getIterator();
	}
	
	public void addKey(String key) {
		keys.add(key);
	}
	
}
