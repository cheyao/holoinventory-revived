package com.cyao.holoinventoryrevived.event;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClientEventHandler {
	private static WeakReference<Level> worldptr = new WeakReference<>(null);
	private static final Cache<BlockPos, Pair<List<ItemStack>, String>> BLOCK_CACHE =
			Caffeine.newBuilder().maximumSize(20).expireAfterWrite(5, TimeUnit.SECONDS).build();

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

		// TODO: Check if mod is enabled or not

		HitResult hit = minecraft.hitResult;
		if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
			return;
		}

		// Limit updating to once per 0.6 seconds
		// TODO: Configurable value
		// TODO: Only re-send packet once every n seconds to account for latency
		BlockPos hitPos = ((BlockHitResult) hit).getBlockPos();
		BlockEntity block = minecraft.level.getBlockEntity(hitPos);
		if (!(block instanceof Container) || (BLOCK_CACHE.getIfPresent(hitPos) != null &&
				(BLOCK_CACHE.policy().expireAfterWrite().get().ageOf(hitPos).get().compareTo(Duration.ofMillis(750)) < 0))) {
			return; // We ain't staring at a container
		}

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

		// Turn off mod according to config settings
		Config config = HoloinventoryRevived.CONFIG;
		if (!(config.ALWAYS_ACTIVE ||
				(config.CONTROL_TRIGGER && Screen.hasControlDown()) ||
				(config.SHIFT_TRIGGER && Screen.hasShiftDown()))) {
			return;
		}

		HitResult hit = minecraft.hitResult;
		if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
			return;
		}

		BlockPos hitPos = ((BlockHitResult) hit).getBlockPos();
		BlockEntity block = minecraft.level.getBlockEntity(hitPos);
		if (!(block instanceof Container)) {
			return;
		}

		Pair<List<ItemStack>, String> items = BLOCK_CACHE.getIfPresent(hitPos);
		if (items == null) {
			return;
		}

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
