package org.adligo.i.adi.shared.models;

import org.adligo.i.util.shared.CollectionFactory;
import org.adligo.i.util.shared.I_Collection;
import org.adligo.i.util.shared.I_Iterator;

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
	/**
	 * allows the CacheRemover to return the size of items
	 * in the Cache (for JUnit tests mostly)
	 */
	public static final short GET_SIZE_TYPE = 2;
	/**
	 * allows the CacheRemover to return the size of items
	 * in the Cache (for JUnit tests mostly)
	 */
	public static final short GET_TIME_INDEX_SIZE_TYPE = 3;
	
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
