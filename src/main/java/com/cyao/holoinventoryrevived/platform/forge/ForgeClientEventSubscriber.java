package com.cyao.holoinventoryrevived.platform.forge;

//? forge {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import com.cyao.holoinventoryrevived.network.InventoryContentsS2CPayload;
import com.cyao.holoinventoryrevived.network.NetworkPayloadHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HoloinventoryRevived.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEventSubscriber {
	@SubscribeEvent
	public static void onDrawLast(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
			return;
		}

		MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance()
				.renderBuffers()
				.bufferSource();
		ClientEventHandler.onRender(event.getPoseStack(), bufferSource, event.getCamera());
		bufferSource.endBatch();
	}
}
*///?}
