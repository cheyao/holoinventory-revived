package com.cyao.holoinventoryrevived;

//? < 1.21 {

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;

public class GlassesArmorMaterial implements ArmorMaterial {
	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		return 100;
	}

	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return 3;
	}

	@Override
	public int getEnchantmentValue() {
		return 25;
	}

	@Override
	public @NonNull SoundEvent getEquipSound() {
		return SoundEvents.ARMOR_EQUIP_GENERIC;
	}

	@Override
	public @NonNull Ingredient getRepairIngredient() {
		return Ingredient.of(Items.GOLD_INGOT);
	}

	@Override
	public @NonNull String getName() {
		return "glasses";
	}

	@Override
	public float getToughness() {
		return 0.0f;
	}

	@Override
	public float getKnockbackResistance() {
		return 0.0f;
	}
}

//? }
