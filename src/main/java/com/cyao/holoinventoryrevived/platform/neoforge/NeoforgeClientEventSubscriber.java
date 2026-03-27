package com.cyao.holoinventoryrevived.platform.neoforge;

//? neoforge {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import com.cyao.holoinventoryrevived.network.NetworkPayloadHandler;
import com.cyao.holoinventoryrevived.network.InventoryContentsS2CPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@EventBusSubscriber(modid = HoloinventoryRevived.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientEventSubscriber {
	//? if >= 1.21.6 {
	/^@SubscribeEvent
	public static void register(RegisterClientPayloadHandlersEvent event) {
		event.register(
				InventoryContentsS2CPayload.ID,
				NeoforgeClientEventSubscriber::clientPayloadHandler
		);
	}
	^///? }

	@SubscribeEvent
	public static void onDrawLast(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
			return;
		}

		MultiBufferSource bufferSource = Minecraft.getInstance()
				.renderBuffers()
				.bufferSource();
		ClientEventHandler.onRender(event.getPoseStack(), bufferSource, event.getCamera());
	}

	public static void clientPayloadHandler(final InventoryContentsS2CPayload data, final IPayloadContext context) {
		NetworkPayloadHandler.inventoryContentsPayloadHandler(data, context.player().level());
	}
}
*///?}
