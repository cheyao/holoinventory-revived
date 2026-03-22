package com.cyao.holoinventoryrevived.platform.fabric;

//? fabric {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class FabricEventSubscriber {

	public static void registerEvents() {
		if (HoloinventoryRevived.xplat().isClient()) {
			ClientTickEvents.START_CLIENT_TICK.register((minecraft) -> {
				ClientEventHandler.onTick();
			});
			WorldRenderEvents.AFTER_ENTITIES.register((context) -> {
				// Guaranteed not null in this event
				assert context.matrixStack() != null;
				assert context.consumers() != null;
				ClientEventHandler.onRender(context.matrixStack(), context.consumers(), context.gameRenderer().getMainCamera());
			});
		}
	}
}

*///?}
