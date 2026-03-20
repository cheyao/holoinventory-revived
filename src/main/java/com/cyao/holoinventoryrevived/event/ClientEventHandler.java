package com.cyao.holoinventoryrevived.event;

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;

public class ClientEventHandler {
	private static WeakReference<Level> worldptr = new WeakReference<>(null);

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
			// TODO: Re-cache
			worldptr = new WeakReference<>(currentWorld);
			return; // We need to re-cache
		}

		// TODO: Check if mod is enabled or not

		HitResult hit = minecraft.hitResult;
		if (hit == null || hit.getType() != HitResult.Type.BLOCK || minecraft.crosshairPickEntity == null) {
			return;
		}

		BlockEntity block = currentWorld.getBlockEntity(minecraft.crosshairPickEntity.blockPosition());

		if (!(block instanceof Container)) {
			return; // We ain't staring at a container
		}
	}

	public static void onRender() {
		if (worldptr.get() == null) {
			return;
		}
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}

		HitResult hit = minecraft.hitResult;
		if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
			return;
		}

		BlockEntity block = minecraft.level.getBlockEntity(((BlockHitResult) hit).getBlockPos());
		if (!(block instanceof Container container)) {
			return;
		}
		int size = container.getContainerSize();

		StringBuilder items = new StringBuilder();
		for (int i = 0; i < size; ++i) {
			ItemStack item = container.getItem(i);
			HoloinventoryRevived.LOGGER.info("{} {}",i, item);
			if (!item.isEmpty()) {
				items.append(item.getDisplayName().getString()).append(" ");
			}
		}

		HoloinventoryRevived.LOGGER.info("Found inventory of size {} with {}", size, items);
	}
}
