package com.cyao.holoinventoryrevived.config;

public class Config {
	public boolean CONTROL_TRIGGER = true;
	public boolean SHIFT_TRIGGER = true;
	public boolean ALWAYS_ACTIVE = false;

	public Config() {
	}

	public Config(Config other) {
		this.CONTROL_TRIGGER = other.CONTROL_TRIGGER;
		this.SHIFT_TRIGGER = other.SHIFT_TRIGGER;
		this.ALWAYS_ACTIVE = other.ALWAYS_ACTIVE;
	}
}
