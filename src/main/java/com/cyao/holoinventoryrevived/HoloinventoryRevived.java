package com.cyao.holoinventoryrevived;

import com.cyao.holoinventoryrevived.platform.Platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import com.cyao.holoinventoryrevived.platform.fabric.FabricPlatform;
//?} neoforge {
/*import com.cyao.holoinventoryrevived.platform.neoforge.NeoforgePlatform;
 *///?} forge {
/*import com.cyao.holoinventoryrevived.platform.forge.ForgePlatform;
*///?}

@SuppressWarnings("LoggingSimilarMessage")
public class HoloinventoryRevived {
	public static final String MOD_ID = /*$ mod_id*/ "holoinventoryrevived";
	public static final String MOD_VERSION = /*$ mod_version*/ "1.0";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "HoloInventory Revived";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		LOGGER.info("Initializing {} on {}", MOD_ID, HoloinventoryRevived.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static void onInitializeClient() {
		LOGGER.info("Initializing {} Client on {}", MOD_ID, HoloinventoryRevived.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		 *///?} forge {
		/*return new ForgePlatform();
		*///?}
	}
}
