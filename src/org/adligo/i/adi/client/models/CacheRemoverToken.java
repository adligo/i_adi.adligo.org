package org.adligo.i.adi.client.models;

import org.adligo.i.util.client.CollectionFactory;
import org.adligo.i.util.client.I_Collection;
import org.adligo.i.util.client.I_Iterator;

public class CacheRemoverToken {
	/**
	 * sweeps all trying to free up some memory somewhere
	 * anything older than stale date (long as date)
	 */
	public static final short SWEEP_ALL_TYPE = 0;
	/**
	 * removes all items from the list
	 */
	public static final short REMOVE_LIST_TYPE =1;
	
	private short type = REMOVE_LIST_TYPE;
	private I_Collection keys = CollectionFactory.create();
	
	/**
	 * the date that 
	 */
	private long staleDate = 0;
	
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
	public void setStaleDate(long staleDate) {
		this.staleDate = staleDate;
	}
	public long getStaleDate() {
		return staleDate;
	}
	
}
