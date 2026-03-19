package com.cyao.holoinventoryrevived;

import com.cyao.holoinventoryrevived.config.Config;
import com.cyao.holoinventoryrevived.platform.Platform;

import me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml.Toml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

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

	public static File CONFIG_FILE;
	public static Config CONFIG;

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		LOGGER.info("Initializing {} on {}", MOD_ID, HoloinventoryRevived.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);

		CONFIG_FILE = HoloinventoryRevived.xplat().getConfigDir().resolve("holoinventory-revived.toml").toFile();
		if (CONFIG_FILE.exists()) {
			LOGGER.info("Reading config from {}", CONFIG_FILE);
			Toml config = new Toml().read(CONFIG_FILE);
			CONFIG = config.to(Config.class);
		} else {
			CONFIG = new Config();
		}
	}

	public static void onInitializeClient() {
		LOGGER.info("Initializing {} Client on {}", MOD_ID, HoloinventoryRevived.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static Platform xplat() {
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
