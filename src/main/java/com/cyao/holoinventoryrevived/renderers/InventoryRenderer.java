package com.cyao.holoinventoryrevived.renderers;

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class InventoryRenderer {
	public static void render(BlockEntity block, List<ItemStack> items, PoseStack matrices, MultiBufferSource renderBuffer, Camera camera) {
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		Vec3 cameraPosition = camera.getPosition();
		Vec3 blockPosition = block.getBlockPos().getCenter();
		Vec3 difference = blockPosition.subtract(cameraPosition);

		matrices.pushPose();
		matrices.translate(difference.x, difference.y + 1, difference.z);
		for (ItemStack item : items) {
			BakedModel blockModel = renderer.getModel(item, block.getLevel(), null, 0);
			renderer.render(item, ItemDisplayContext.FIXED, false, matrices, renderBuffer, 15728640, OverlayTexture.NO_OVERLAY, blockModel);
		}
		HoloinventoryRevived.LOGGER.info("{}", difference);
		matrices.popPose();
	}
}
