package com.cyao.holoinventoryrevived;

import com.cyao.holoinventoryrevived.config.Config;
import com.cyao.holoinventoryrevived.network.NetworkClient;
import com.cyao.holoinventoryrevived.platform.Platform;

import me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml.Toml;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Unbreakable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

//? fabric {
import com.cyao.holoinventoryrevived.platform.fabric.FabricPlatform;
import com.cyao.holoinventoryrevived.platform.fabric.FabricNetworkClient;
import net.minecraft.world.item.CreativeModeTabs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
//?} neoforge {
/*import com.cyao.holoinventoryrevived.platform.neoforge.NeoforgePlatform;
import com.cyao.holoinventoryrevived.platform.neoforge.NeoforgeNetworkClient;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;
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

	//? if fabric {
	public static Item HOLO_GLASSES_ITEM;
	//? } else if neoforge {
	/*public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HoloinventoryRevived.MOD_ID);
	public static final DeferredItem<ArmorItem> HOLO_GLASSES_ITEM = ITEMS.register("holo_glasses", () ->
			new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.HELMET,
					new Item.Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(false))));
	*///? }

	private static final Platform PLATFORM = createPlatformInstance();
	private static final NetworkClient NETWORK = createNetworkInstance();

	public static void onInitialize() {
		LOGGER.info("Initializing {} on {}", MOD_ID, HoloinventoryRevived.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);

		// Read in config file if it exists
		CONFIG_FILE = HoloinventoryRevived.xplat().getConfigDir().resolve("holoinventory-revived.toml").toFile();
		if (CONFIG_FILE.exists()) {
			LOGGER.info("Reading config from {}", CONFIG_FILE);
			Toml config = new Toml().read(CONFIG_FILE);
			CONFIG = config.to(Config.class);
		} else {
			CONFIG = new Config();
		}

		// Register our glasses item
		//? if fabric {
		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "holo_glasses");
		ArmorItem holoGlasses = new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, new Item.Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(false)));
		HOLO_GLASSES_ITEM = Registry.register(BuiltInRegistries.ITEM, itemID, holoGlasses);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((itemGroup) -> itemGroup.accept(HOLO_GLASSES_ITEM));
		//? }
	}

	public static void onInitializeClient() {
		LOGGER.info("Initializing {} Client on {}", MOD_ID, HoloinventoryRevived.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	public static NetworkClient xnetwork() {
		return NETWORK;
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

	private static NetworkClient createNetworkInstance() {
		//? fabric {
		return new FabricNetworkClient();
		//?} neoforge {
		/*return new NeoforgeNetworkClient();
		 *///?} forge {
		/*return new ForgePlatform();
		 *///?}
	}
}
