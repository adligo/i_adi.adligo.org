package org.adligo.i.adi.client.models;

public class CacheValue {
	/**
	 * 60 seconds per mintue
	 * 1000 milliseconds per second
	 */
	private static final long MILLIS_PER_MINUTE = 60 *1000;
	private long putTime;
	private Object value;
	private String fullPath;

	public CacheValue(String pFullPath, long pPutTime, Object pValue) {
		fullPath = pFullPath;
		putTime = pPutTime;
		value = pValue;
	}
	
	public String getFullPath() {
		return fullPath;
	}
	public long getPutTime() {
		return putTime;
	}
	public Object getValue() {
		return value;
	}
	
	/**
	 * this returns a String for the ReferenceDomain index of 
	 * CacheValues by time, it is slash and then the number of minutes
	 * before or since the Timestamp 0, and then slash and 
	 * the long putTime value
	 *  
	 *  note that the time crunch string for the minute around (plus or minus) the
	 *  Timestamp 0 is actually two minutes long, but since most clocks 
	 *  will not be in the year 1970 anytime it doesn't matter much
	 *  
	 * @return
	 */
	public String getTimeCrunchString() {
		long min = putTime/MILLIS_PER_MINUTE;
		StringBuffer sb = new StringBuffer();
		sb.append("/");
		sb.append(min);
		sb.append("/");
		sb.append(putTime);
		return sb.toString();
	}
	
	/**
	 * for lookups
	 */
	public long getTopTimeCrunch() {
		return putTime/MILLIS_PER_MINUTE;
	}
	
	public long getTopTimeFromCrunchString(String timeCrunchString) {
		String withoutSlash = timeCrunchString.substring(1, timeCrunchString.length());
		return Long.valueOf(withoutSlash).longValue();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CacheValue [putTime=");
		sb.append(putTime);
		sb.append(",value=");
		sb.append(value);
		sb.append("]");
		return sb.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullPath == null) ? 0 : fullPath.hashCode());
		result = prime * result + (int) (putTime ^ (putTime >>> 32));
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheValue other = (CacheValue) obj;
		if (fullPath == null) {
			if (other.fullPath != null)
				return false;
		} else if (!fullPath.equals(other.fullPath))
			return false;
		if (putTime != other.putTime)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
