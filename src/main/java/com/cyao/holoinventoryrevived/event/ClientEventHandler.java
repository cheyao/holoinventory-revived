package com.cyao.holoinventoryrevived.event;

import com.cyao.holoinventoryrevived.GlassesItem;
import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.config.Config;
import com.cyao.holoinventoryrevived.renderers.InventoryRenderer;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//? if fabric {
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
//? } else if neoforge {
/*import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
*///? }

public class ClientEventHandler {
	private static WeakReference<Level> worldptr = new WeakReference<>(null);
	private static final Cache<BlockPos, Pair<List<ItemStack>, String>> BLOCK_CACHE =
			Caffeine.newBuilder().maximumSize(20).expireAfterWrite(5, TimeUnit.SECONDS).build();
	private static final Cache<BlockPos, Boolean> REQUESTED =
			Caffeine.newBuilder().maximumSize(20).expireAfterWrite(250, TimeUnit.MILLISECONDS).build();

	// Turn off mod according to config settings
	private static boolean modDisabled(Player player) {
		Config config = HoloinventoryRevived.CONFIG;
		return !((config.ALWAYS_ACTIVE) ||
				 (config.CONTROL_TRIGGER && Screen.hasControlDown()) ||
				 (config.SHIFT_TRIGGER && Screen.hasShiftDown()) ||
				 (player.getInventory().getArmor(3).is(GlassesItem.HOLO_GLASSES_ITEM)));
	}

	private static boolean isContainer(BlockPos pos) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player == null) {
			return false;
		}
		Level level = minecraft.player.level();
		BlockEntity block = level.getBlockEntity(pos);

		//? if (neoforge || forge) {
		/*IItemHandler handler = level.getCapability(
				Capabilities.ItemHandler.BLOCK,
				pos,
				null
		);
		*///? } else {
		Storage<ItemVariant> handler = ItemStorage.SIDED.find(level, pos, null);
		 //? }

		return (block instanceof Container) ||
				(block instanceof EnderChestBlockEntity) ||
				(handler != null);
	}

	public static void onTick() {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.isPaused()) {
			return;
		}
		Level currentWorld = minecraft.level;
		if (currentWorld == null) {
			return;
		}

		// Caching
		Level cachedWorld = worldptr.get();
		if (cachedWorld == null || currentWorld != cachedWorld) {
			BLOCK_CACHE.cleanUp();
			worldptr = new WeakReference<>(currentWorld);
			return; // We need to re-cache
		}

		if (modDisabled(minecraft.player)) {
			return;
		}

		HitResult hit = minecraft.hitResult;
		if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
			return;
		}

		// Limit updating to once per 0.6 seconds
		BlockPos hitPos = ((BlockHitResult) hit).getBlockPos();

		if (!isContainer(hitPos) || // Not a container
				(REQUESTED.getIfPresent(hitPos) != null) || // Already exists in cache
				(BLOCK_CACHE.getIfPresent(hitPos) != null &&
						(BLOCK_CACHE.policy().expireAfterWrite().get().ageOf(hitPos)
								.get().compareTo(Duration.ofMillis(750)) < 0))) {
			return; // We ain't staring at a container
		}

		// Limit packets to one every 0.25s to help with server load
		REQUESTED.put(hitPos, true);
		HoloinventoryRevived.xnetwork().cacheInventory(hitPos);
	}

	public static void onRender(PoseStack matrices, MultiBufferSource renderBuffer, Camera camera) {
		if (worldptr.get() == null) {
			return;
		}
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}

		if (modDisabled(minecraft.player)) {
			return;
		}

		HitResult hit = minecraft.hitResult;
		if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
			return;
		}

		BlockPos hitPos = ((BlockHitResult) hit).getBlockPos();
		if (!isContainer(hitPos)) {
			return;
		}

		Pair<List<ItemStack>, String> items = BLOCK_CACHE.getIfPresent(hitPos);
		if (items == null) {
			return;
		}

		BlockEntity block = minecraft.level.getBlockEntity(hitPos);
		InventoryRenderer.render(block, items.getFirst(), items.getSecond(), matrices, renderBuffer, camera);
	}

	public static void cacheBlock(BlockPos block, List<ItemStack> items, String name) {
		List<ItemStack> dedupedItems = new ArrayList<>(items.size());

		for (ItemStack item : items) {
			boolean merged = false;
			for (ItemStack content : dedupedItems) {
				if (ItemStack.isSameItem(item, content)) {
					content.grow(item.getCount());

					merged = true;
					break;
				}
			}

			if (!merged) {
				dedupedItems.add(item);
			}
		}

		BLOCK_CACHE.put(block, new Pair<>(dedupedItems, name));

		HoloinventoryRevived.LOGGER.debug("Cached inventory at {}", block);
	}
}
