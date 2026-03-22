package com.cyao.holoinventoryrevived;

import com.cyao.holoinventoryrevived.config.Config;
import com.cyao.holoinventoryrevived.network.NetworkClient;
import com.cyao.holoinventoryrevived.platform.Platform;

import me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml.Toml;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.crafting.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

//? fabric {
/*import com.cyao.holoinventoryrevived.platform.fabric.FabricPlatform;
import com.cyao.holoinventoryrevived.platform.fabric.FabricNetworkClient;
import net.minecraft.world.item.CreativeModeTabs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.Map;
*///?} neoforge {
import com.cyao.holoinventoryrevived.platform.neoforge.NeoforgePlatform;
import com.cyao.holoinventoryrevived.platform.neoforge.NeoforgeNetworkClient;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorMaterials;

import java.util.EnumMap;
//?} forge {
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
	/*public static Item HOLO_GLASSES_ITEM;
	*///? } else if neoforge {
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MOD_ID);
	public static final Holder<ArmorMaterial> ARMOR_MATERIAL =
			ARMOR_MATERIALS.register("copper", () -> new ArmorMaterial(
					Util.make(new EnumMap<>(ArmorItem.Type.class), map -> { map.put(ArmorItem.Type.HELMET, 3); }),
					25, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(Tags.Items.INGOTS_COPPER),
					List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MOD_ID, "glasses"))),
					0, 0
			));

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
	public static final DeferredItem<ArmorItem> HOLO_GLASSES_ITEM = ITEMS.register("holo_glasses", () ->
			new ArmorItem(ARMOR_MATERIAL, ArmorItem.Type.HELMET,
					new Item.Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(false))));
	//? }

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
		/*List<ArmorMaterial.Layer> layers = List.of(
				new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MOD_ID, "glasses"), "", false)
		);
		ArmorMaterial material = new ArmorMaterial(Map.of(
				ArmorItem.Type.HELMET, 3
		), 25, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(Items.GOLD_INGOT), layers, 0.0f, 0.0f);
		material = Registry.register(BuiltInRegistries.ARMOR_MATERIAL, ResourceLocation.fromNamespaceAndPath(MOD_ID, "glasses"), material);

		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "holo_glasses");
		ArmorItem holoGlasses = new ArmorItem(Holder.direct(material), ArmorItem.Type.HELMET, new Item.Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(false)));
		HOLO_GLASSES_ITEM = Registry.register(BuiltInRegistries.ITEM, itemID, holoGlasses);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((itemGroup) -> itemGroup.accept(HOLO_GLASSES_ITEM));
		*///? }
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
		/*return new FabricPlatform();
		*///?} neoforge {
		return new NeoforgePlatform();
		 //?} forge {
		/*return new ForgePlatform();
		*///?}
	}

	private static NetworkClient createNetworkInstance() {
		//? fabric {
		/*return new FabricNetworkClient();
		*///?} neoforge {
		return new NeoforgeNetworkClient();
		 //?} forge {
		/*return new ForgePlatform();
		 *///?}
	}
}
