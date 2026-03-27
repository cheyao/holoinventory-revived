package com.cyao.holoinventoryrevived.platform.fabric;

//? fabric {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.network.GetInventoryC2SPayload;
import com.cyao.holoinventoryrevived.network.InventoryContentsS2CPayload;
import com.cyao.holoinventoryrevived.network.NetworkClient;
import com.cyao.holoinventoryrevived.network.NetworkPayloadHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

//? if >= 1.20.5
//import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class FabricNetworkClient implements NetworkClient {
	public static void onInitialize() {
		//? if < 1.20.5 {
		ServerPlayNetworking.registerGlobalReceiver(GetInventoryC2SPayload.ID,
				(server, player, handler, buf, responseSender) -> {
					GetInventoryC2SPayload payload = GetInventoryC2SPayload.decode(buf);

					server.execute(() -> {
						NetworkPayloadHandler.getInventoryPayloadHandler(payload, player);
					});
				});
		//? } else {
		/^PayloadTypeRegistry.playC2S().register(GetInventoryC2SPayload.ID, GetInventoryC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(InventoryContentsS2CPayload.ID, InventoryContentsS2CPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(GetInventoryC2SPayload.ID,
			(payload, context) -> {
				NetworkPayloadHandler.getInventoryPayloadHandler(payload, context.player());
			});
		^///? }
	}

	public static void onInitializeClient() {
		//? if < 1.20.5 {
		ClientPlayNetworking.registerGlobalReceiver(InventoryContentsS2CPayload.ID,
				(client, handler, buf, responseSender) -> {
					ClientLevel level = client.level;
					InventoryContentsS2CPayload payload = InventoryContentsS2CPayload.decode(buf);

					NetworkPayloadHandler.inventoryContentsPayloadHandler(payload, level);
				});
		//? } else {
		/^ClientPlayNetworking.registerGlobalReceiver(InventoryContentsS2CPayload.ID,
			(payload, context) -> {
				ClientLevel level = context.client().level;

				NetworkPayloadHandler.inventoryContentsPayloadHandler(payload, level);
			});
		^///? }
	}

	@Override
	public void cacheInventory(BlockPos block) {
		//? if < 1.20.5
		FriendlyByteBuf payload = GetInventoryC2SPayload.encode(block);
		//? if >= 1.20.5
		//GetInventoryC2SPayload payload = new GetInventoryC2SPayload(block);

		ClientPlayNetworking.send(
				//? if < 1.20.5
				GetInventoryC2SPayload.ID,
				payload
		);
	}
}

*///? }
