package com.cyao.holoinventoryrevived;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Unbreakable;

import java.util.Map;
import java.util.function.Supplier;

//? 1.21 || 1.21.1 {
/*import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
*///? }

//? 1.21.2 {
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
//? }

//? fabric {
/*import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
*///? }

//? neoforge {
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
//? }

import static com.cyao.holoinventoryrevived.HoloinventoryRevived.MOD_ID;

public class GlassesItem {
	//? neoforge {
	//? 1.21 || 1.21.1
	//public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MOD_ID);

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
	//? }

	public static final Holder<ArmorMaterial> GLASSES_MATERIAL = registerMaterial("glasses");

	//? fabric
	//public static final Item HOLO_GLASSES_ITEM = register(GLASSES_MATERIAL, "holo_glasses");
	//? neoforge
	public static final DeferredItem<ArmorItem> HOLO_GLASSES_ITEM = register(GLASSES_MATERIAL, "holo_glasses");

	public static Holder<ArmorMaterial> registerMaterial(String id) {
		ResourceLocation materialID = ResourceLocation.fromNamespaceAndPath(MOD_ID, id);

		var armorMap = Map.of(ArmorType.HELMET, 3);

		//? if 1.21 || 1.21.1 {
		/*var layers = List.of(
				new ArmorMaterial.Layer(materialID, "", false)
		);
		var repairIngredient = Ingredient.of(Items.GOLD_INGOT);
		*///? }

		//? if 1.21.2
		var repairIngredient = ItemTags.REPAIRS_GOLD_ARMOR;

		//? if 1.21 || 1.21.1
		//ArmorMaterial material = new ArmorMaterial(armorMap, 25, SoundEvents.ARMOR_EQUIP_GENERIC, () -> repairIngredient, layers, 0.0f, 0.0f);
		//? if 1.21.2
		ArmorMaterial material = new ArmorMaterial(15, armorMap, 25, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0f, 0.0f, repairIngredient, materialID);

		//? if fabric && (1.21 || 1.21.1)
		//return Holder.direct(Registry.register(BuiltInRegistries.ARMOR_MATERIAL, materialID, material));

		//? if neoforge && (1.21 || 1.21.1)
		//return ARMOR_MATERIALS.register(id, () -> material);

		//? if 1.21.2
		return Holder.direct(material);
	}

	//? fabric
	//public static Item register(Holder<ArmorMaterial> armorMaterial, String id) {
	//? neoforge
	public static DeferredItem<ArmorItem> register(Holder<ArmorMaterial> armorMaterial, String id) {
		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(MOD_ID, id);

		//? 1.21 || 1.21.1
		//Holder<ArmorMaterial> material = armorMaterial;
		//? 1.21.2
		 ArmorMaterial material = armorMaterial.value();

		Supplier<ArmorItem> item =  () -> new ArmorItem(material, ArmorType.HELMET, new Item.Properties()
		//? 1.21.2
				.setId(ResourceKey.create(Registries.ITEM, itemID))
				.component(DataComponents.UNBREAKABLE, new Unbreakable(false)));

		//? fabric {
		/*return Registry.register(BuiltInRegistries.ITEM, itemID, item.get());
		*///? }

		//? neoforge {
		return ITEMS.register(id, item);
		//? }
	}

	public static void initialize() {
		//? fabric {
		/*ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((itemGroup) -> itemGroup.accept(HOLO_GLASSES_ITEM));
		*///? }
	}
}
