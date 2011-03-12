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
}
