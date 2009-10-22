package org.adligo.i.adi.client.models;

public class ConfigRequest {
	private String key;
	private Object defaultSetting;
	
	public ConfigRequest(String p_key, Object p_defaultSetting) {
		key = p_key;
		defaultSetting = p_defaultSetting;
	}
	
	public ConfigRequest(Object p_defaultSetting) {
		defaultSetting = p_defaultSetting;
	}
	
	public Object getDefaultSetting() {
		return defaultSetting;
	}

	public void setDefaultSetting(Object defaultSetting) {
		this.defaultSetting = defaultSetting;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
