package com.cyao.holoinventoryrevived.platform.neoforge;

//? neoforge {

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.network.GetInventoryC2SPayload;
import com.cyao.holoinventoryrevived.network.InventoryContentsS2CPayload;
import com.cyao.holoinventoryrevived.network.NetworkPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

//? <=1.21.2
//@EventBusSubscriber(modid = HoloinventoryRevived.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
//? > 1.21.2
@EventBusSubscriber(modid = HoloinventoryRevived.MOD_ID)
public class NeoforgeModEventSubscriber {
	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar("1");

		registrar.playToServer(
				GetInventoryC2SPayload.ID,
				GetInventoryC2SPayload.CODEC,
				new MainThreadPayloadHandler<>(
						NeoforgeModEventSubscriber::serverPayloadHandler
				)
			);

		//? if <=1.21.6 {
		registrar.playToClient(
				InventoryContentsS2CPayload.ID,
				InventoryContentsS2CPayload.CODEC,
				new MainThreadPayloadHandler<>(
						NeoforgeClientEventSubscriber::clientPayloadHandler
				)
		);
		//? }
	}

	private static void serverPayloadHandler(final GetInventoryC2SPayload payload, final IPayloadContext context) {
		NetworkPayloadHandler.getInventoryPayloadHandler(payload, context.player());
	}
}
//?}
