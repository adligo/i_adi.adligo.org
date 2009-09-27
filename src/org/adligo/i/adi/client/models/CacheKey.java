package org.adligo.i.adi.client.models;

public class CacheKey {
	private String name;
	private long cache_entry_time = System.currentTimeMillis();
	/**
	 * 0 means never evict from cache
	 */
	private int seconds_to_live = 0;
	
	public CacheKey(String name) {
		this.name = name;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getSeconds_to_live() {
		return seconds_to_live;
	}

	public void setSeconds_to_live(int secondsToLive) {
		seconds_to_live = secondsToLive;
	}

	
	public boolean shouldEvict() {
		long now = System.currentTimeMillis();
		long delta = now - cache_entry_time;
		int deltaSecs = new Double(delta / 1000).intValue();
		if (deltaSecs > seconds_to_live) {
			return true;
		}
		return false;
	}
}
