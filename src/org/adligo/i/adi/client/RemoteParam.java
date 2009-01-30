package org.adligo.i.adi.client;

public class RemoteParam {
	private String remoteAdiName;
	private Object value;
	
	public String getRemoteAdiName() {
		return remoteAdiName;
	}
	public void setRemoteAdiName(String remoteAdiName) {
		this.remoteAdiName = remoteAdiName;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RemoteParam [adi_name=");
		sb.append(remoteAdiName);
		sb.append(",value");
		sb.append(value);
		sb.append("]");
		return sb.toString();
	}
}
