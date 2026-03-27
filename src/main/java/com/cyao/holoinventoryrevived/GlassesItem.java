package com.cyao.holoinventoryrevived;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

//? < 1.21.2
//import net.minecraft.world.item.ArmorMaterial;
//? < 1.21.5
//import net.minecraft.world.item.ArmorItem;

//? >= 1.21 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
//? <1.21.5
//import net.minecraft.world.item.component.Unbreakable;

import java.util.List;
import java.util.Map;
//? }

//? >=1.21.2 {
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
//? }

//? >=1.21.5 {
import net.minecraft.util.Unit;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
//? }

//? fabric {
/*import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
*///? }

//? neoforge {
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
//? }

//? forge {
/*import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
*///? }

import static com.cyao.holoinventoryrevived.HoloinventoryRevived.MOD_ID;

public class GlassesItem {
	//? neoforge || forge{
	//? 1.21 || 1.21.1
	//public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MOD_ID);

	//? forge
	//public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
	//? neoforge
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
	//? }

	public static final Holder<ArmorMaterial> GLASSES_MATERIAL = registerMaterial("glasses");

	//? fabric
	//public static final Item HOLO_GLASSES_ITEM = register(GLASSES_MATERIAL, "holo_glasses");
	//? neoforge
	public static final DeferredItem</*? <=1.21.4 { *//*ArmorItem*//*? } else {*/Item/*? } */> HOLO_GLASSES_ITEM = register(GLASSES_MATERIAL, "holo_glasses");
	//? forge
	//public static final RegistryObject<ArmorItem> HOLO_GLASSES_ITEM = register(GLASSES_MATERIAL, "holo_glasses");

	public static Holder<ArmorMaterial> registerMaterial(String id) {
		//? < 1.21
		//return Holder.direct(new GlassesArmorMaterial());

		//? >= 1.21 {

		//? <=1.21.4
		//ResourceLocation materialID = ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
		//? >=1.21.5
		ResourceKey<EquipmentAsset> materialID = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MOD_ID, id));

		//? if 1.21 || 1.21.1 {
		/*var layers = List.of(
				new ArmorMaterial.Layer(materialID, "", false)
		);
		var repairIngredient = Ingredient.of(Items.GOLD_INGOT);
		*///? }

		//? if >=1.21.2
		var repairIngredient = ItemTags.REPAIRS_GOLD_ARMOR;

		var armorMap = Map.of(ArmorType.HELMET, 3);

		//? if 1.21 || 1.21.1
		//ArmorMaterial material = new ArmorMaterial(armorMap, 25, SoundEvents.ARMOR_EQUIP_GENERIC, () -> repairIngredient, layers, 0.0f, 0.0f);
		//? if >=1.21.2
		ArmorMaterial material = new ArmorMaterial(15, armorMap, 25, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0f, 0.0f, repairIngredient, materialID);

		//? if fabric && (1.21 || 1.21.1)
		//return Holder.direct(Registry.register(BuiltInRegistries.ARMOR_MATERIAL, materialID, material));

		//? if neoforge && (1.21 || 1.21.1)
		//return ARMOR_MATERIALS.register(id, () -> material);

		//? if >=1.21.2
		return Holder.direct(material);

		//? }
	}

	//? fabric
	//public static Item register(Holder<ArmorMaterial> armorMaterial, String id) {
	//? neoforge
	public static DeferredItem</*? <=1.21.4 { *//*ArmorItem*//*? } else {*/Item/*? } */> register(Holder<ArmorMaterial> armorMaterial, String id) {
	//? forge
	//public static RegistryObject<ArmorItem> register(Holder<ArmorMaterial> armorMaterial, String id) {
		//? >= 1.21
		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
		//? fabric && < 1.21
		//ResourceLocation itemID = new ResourceLocation(MOD_ID, id);

		//? 1.21 || 1.21.1
		//Holder<ArmorMaterial> material = armorMaterial;
		//? !(1.21 || 1.21.1)
		ArmorMaterial material = armorMaterial.value();

		//? <=1.21.4 {
		/*Supplier<ArmorItem> item =  () -> new ArmorItem(material, ArmorType.HELMET, new Item.Properties()
				//? >= 1.21.2
				.setId(ResourceKey.create(Registries.ITEM, itemID))
				//? >= 1.21
				.component(DataComponents.UNBREAKABLE, new Unbreakable(false)));
				//? < 1.21
				//.durability(99999));
		*///? }

		//? >=1.21.5 {
		Supplier<Item> item = () ->
				new Item(new Item.Properties().humanoidArmor(material, ArmorType.HELMET)
						.component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
						.setId(ResourceKey.create(Registries.ITEM, itemID)));
		//? }

		//? fabric
		//return Registry.register(BuiltInRegistries.ITEM, itemID, item.get());

		//? neoforge || forge
		return ITEMS.register(id, item);
	}

	public static void initialize() {
		//? fabric {
		/*ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register((itemGroup) -> itemGroup.accept(HOLO_GLASSES_ITEM));
		*///? }
	}
}
