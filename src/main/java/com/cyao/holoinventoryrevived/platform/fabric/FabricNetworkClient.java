package com.cyao.holoinventoryrevived.platform.fabric;

//? fabric {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import com.cyao.holoinventoryrevived.network.NetworkClient;
import com.cyao.holoinventoryrevived.network.NetworkPayloadHandler;
import com.cyao.holoinventoryrevived.network.NetworkPayloads;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FabricNetworkClient implements NetworkClient {
	public static void onInitialize() {
		PayloadTypeRegistry.playC2S().register(NetworkPayloads.GetInventoryC2SPayload.ID, NetworkPayloads.GetInventoryC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(NetworkPayloads.InventoryContentsS2CPayload.ID, NetworkPayloads.InventoryContentsS2CPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(NetworkPayloads.GetInventoryC2SPayload.ID,
			(payload, context) -> {
				NetworkPayloadHandler.getInventoryPayloadHandler(payload, context.player());
			});
	}

	public static void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(NetworkPayloads.InventoryContentsS2CPayload.ID,
			(payload, context) -> {
				ClientLevel level = context.client().level;

				NetworkPayloadHandler.inventoryContentsPayloadHandler(payload, level);
			});
	}

	@Override
	public void cacheInventory(BlockPos block) {
		NetworkPayloads.GetInventoryC2SPayload payload = new NetworkPayloads.GetInventoryC2SPayload(block);
		ClientPlayNetworking.send(payload);
	}
}

*///? }
