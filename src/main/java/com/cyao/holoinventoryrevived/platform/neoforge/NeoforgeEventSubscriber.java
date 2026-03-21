package com.cyao.holoinventoryrevived.platform.neoforge;

//? neoforge {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber
public class NeoforgeEventSubscriber {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event) {
		if (HoloinventoryRevived.xplat().isClient()) {
			ClientEventHandler.onTick();
		}
	}

	@SubscribeEvent
	public static void onDrawLast(RenderLevelStageEvent event) {
		if (HoloinventoryRevived.xplat().isClient()) {
			ClientEventHandler.onRender();
		}
	}
}
*///?}
