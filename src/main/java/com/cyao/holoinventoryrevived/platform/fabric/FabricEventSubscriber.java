package com.cyao.holoinventoryrevived.platform.fabric;

//? fabric {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber
public class NeoforgeEventSubscriber {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event) {
		ClientEventHandler.onTick();
	}
}
//?}
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FabricEventSubscriber {
	public static void registerEvents() {
		if (HoloinventoryRevived.xplat().isClient()) {
			ClientTickEvents.START_CLIENT_TICK.register((minecraft) -> {
				ClientEventHandler.onTick();
			});
		}
	}
}
*///?}
