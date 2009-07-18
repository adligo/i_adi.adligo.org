package org.adligo.i.adi.client;

public class ConfigRequest {
	private Object defaultSetting;

	public ConfigRequest(Object p_defaultSetting) {
		defaultSetting = p_defaultSetting;
	}
	public Object getDefaultSetting() {
		return defaultSetting;
	}

	public void setDefaultSetting(Object defaultSetting) {
		this.defaultSetting = defaultSetting;
	}
	
}
