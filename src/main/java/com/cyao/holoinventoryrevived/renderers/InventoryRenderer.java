package com.cyao.holoinventoryrevived.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class InventoryRenderer {
	private static void renderItem(Level world, ItemStack item, PoseStack matrices, MultiBufferSource renderBuffer) {
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		BakedModel blockModel = renderer.getModel(item, world, null, 0);

		matrices.pushPose();
		if (!blockModel.usesBlockLight()) {
			// Unify item & block scale
			matrices.scale(0.75f, 0.75f, 0.75f);
		}
		matrices.scale(0.45f, 0.45f, 0.45f);


		matrices.rotateAround(Axis.YP.rotationDegrees((float) (360 * (System.currentTimeMillis() & 0x3FFFL) / (double) 0x3FFFL)), 0, 0, 0);
		renderer.render(item, ItemDisplayContext.FIXED, false, matrices, renderBuffer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, blockModel);
		matrices.popPose();
	}

	private static void renderText(ItemStack item, PoseStack matrices, MultiBufferSource renderBuffer) {
		Font textRenderer = Minecraft.getInstance().font;

		// Now render text
		matrices.pushPose();
		matrices.translate(-0.175f, -0.14f, -0.05f);
		matrices.scale(0.02f, 0.02f, 0.02f);
		matrices.scale(0.55f, 0.55f, -0.55f);
		matrices.rotateAround(Axis.ZP.rotationDegrees(180), 0, 0, 0);

		String size;
		if (item.getCount() < 1000) size = String.valueOf(item.getCount());
		else if (item.getCount() < 1000_000) size = item.getCount() / 1000 + "k";
		else size = item.getCount() / (1000_000) + "M";

		int width = textRenderer.width(size);
		RenderSystem.disableDepthTest();
		textRenderer.drawInBatch(size,
				(float) -(width / 2.0f), 0f, 0xFFFFFFFF,
				true, matrices.last().pose(), renderBuffer,
				Font.DisplayMode.POLYGON_OFFSET, 0, LightTexture.FULL_BRIGHT);
		matrices.popPose();
	}

	public static void render(BlockEntity block, List<ItemStack> items, String name, PoseStack matrices,
							  MultiBufferSource renderBuffer, Camera camera) {
		if (items.isEmpty()) {
			return;
		}

		Vec3 cameraPosition = camera.getPosition();
		Vec3 blockPosition = block.getBlockPos().getCenter();
		Vec3 difference = blockPosition.subtract(cameraPosition);
		Font textRenderer = Minecraft.getInstance().font;

		matrices.pushPose();

		//? < 1.21
		matrices.translate(difference.x, difference.y, difference.z);

		matrices.rotateAround(camera.rotation(), 0, 0, 0);
		//? >= 1.21
		 //matrices.rotateAround(Axis.YP.rotationDegrees(180), 0, 0, 0);
		//? >= 1.21
		 //matrices.translate(0, 0.0f, difference.length() - 0.75f);

		//? < 1.21
		matrices.translate(0, 0.0f, -0.75f);

		double distance = difference.length();
		matrices.scale((float) (distance * 0.2f), (float) (distance * 0.2f), (float) (distance * 0.2f));

		// Code from https://github.com/dries007/HoloInventory/blob/master/src/main/java/net/dries007/holoInventory/client/renderers/InventoryRenderer.java#L94-L101
		// MIT License
		int cols;
		if (items.size() <= 9) cols = items.size();
		else if (items.size() <= 27) cols = 9;
		else if (items.size() <= 54) cols = 11;
		else if (items.size() <= 90) cols = 14;
		else if (items.size() <= 109) cols = 18;
		else cols = 21;
		int rows = 1 + ((items.size() % cols == 0) ? (items.size() / cols) - 1 : items.size() / cols);
		// End copy

		if (rows > 4) {
			matrices.scale(0.8f, 0.8f, 0.8f);
		}

		// Render block name
		matrices.pushPose();
		matrices.translate(0,  0.2f * rows + 0.6f, 0);
		matrices.scale(0.03f, 0.03f, 0.03f);
		matrices.rotateAround(Axis.ZP.rotationDegrees(180), 0, 0, 0);

		int width = textRenderer.width(name);
		// RenderSystem.disableDepthTest();
		textRenderer.drawInBatch(name,
				(float) -(width / 2.0f), 0f, 0xFFFFFFFF,
				false, matrices.last().pose(), renderBuffer,
				Font.DisplayMode.POLYGON_OFFSET, 0, LightTexture.FULL_BRIGHT);
		matrices.popPose();

		int col = 0, row = 0;
		for (ItemStack item : items) {
			// Layer on unique matrix for each item
			matrices.pushPose();
			matrices.translate(0.4f * (cols / 2.0 - col) - 0.2f, 0.4f * (rows / 2.0 - row), 0);
			renderItem(block.getLevel(), item, matrices, renderBuffer);
			renderText(item, matrices, renderBuffer);
			matrices.popPose();

			++col;
			if (col == cols) {
				++row;
				col = 0;
			}
		}
		matrices.popPose();
	}
}
