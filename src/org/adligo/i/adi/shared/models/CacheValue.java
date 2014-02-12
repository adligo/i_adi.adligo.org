package org.adligo.i.adi.shared.models;

public class CacheValue {
	/**
	 * 60 seconds per mintue
	 * 1000 milliseconds per second
	 */
	private static final long MILLIS_PER_MINUTE = 60 *1000;
	private long putTime;
	private Object value;
	private String fullPath;
	private ReferenceAddressName refName;

	public CacheValue(String pKey, long pPutTime, Object pValue) {
		init(new ReferenceAddressName(pKey), pPutTime, pValue);
	}
	
	public CacheValue(ReferenceAddressName pRefName, long pPutTime, Object pValue) {
		init(pRefName, pPutTime, pValue);
	}
	private void init(ReferenceAddressName pRefName, long pPutTime,
			Object pValue) {
		refName = pRefName;
		fullPath = pRefName.getFullPath();
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
		//note @parentFullPath was added 
		// to remove contention locks in the time index
		// where many threads would be writing to the same
		// child Map (java.util.concurrentHashMap) 
		// designated by the minute 
		sb.append(refName.getParentFullPath());
		sb.append("/");
		sb.append(putTime);
		sb.append(getLocalWithAtInsteadOfSlash());
		return sb.toString();
	}
	
	private String getLocalWithAtInsteadOfSlash() {
		String local = refName.getLocalPath();
		char [] chars = local.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '/') {
				sb.append('@');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();	
	}
	/**
	 * for lookups
	 */
	public long getTopTimeCrunch() {
		return putTime/MILLIS_PER_MINUTE;
	}
	
	public long getTopTimeFromCrunchString(String timeCrunchString) {
		char [] chars = timeCrunchString.toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean firstSlash = false;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (firstSlash) {
				if (c == '/') {
					break;
				} else {
					sb.append(c);
				}
			} else {
				if (c == '/') {
					firstSlash = true;
				}
			}
		}
		return Long.parseLong(sb.toString());
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CacheValue [fullPath=");
		sb.append(fullPath);
		sb.append(",putTime=");
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
