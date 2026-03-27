package com.cyao.holoinventoryrevived.platform.forge;

//? forge {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HoloinventoryRevived.MOD_ID)
public class ForgeEventSubscriber {
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START && HoloinventoryRevived.xplat().isClient()) {
			ClientEventHandler.onTick();
		}
	}
}
*///?}
