package com.cyao.holoinventoryrevived.platform.fabric;

//? fabric {

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import com.cyao.holoinventoryrevived.network.NetworkClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FabricNetworkClient implements NetworkClient {
	public record GetInventoryC2SPayload(BlockPos pos) implements CustomPacketPayload {
		public static final ResourceLocation GET_INVENTORY_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "get_inventory");
		public static final Type<GetInventoryC2SPayload> ID = new Type<>(GET_INVENTORY_PAYLOAD_ID);
		public static final StreamCodec<RegistryFriendlyByteBuf, GetInventoryC2SPayload> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, GetInventoryC2SPayload::pos, GetInventoryC2SPayload::new);

		@NotNull
		@Override
		public Type<? extends CustomPacketPayload> type() {
			return ID;
		}
	}

	public record InventoryContentsS2CPayload(BlockPos pos, List<ItemStack> items) implements CustomPacketPayload {
		public static final ResourceLocation GET_INVENTORY_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "inventory_contents");
		public static final Type<InventoryContentsS2CPayload> ID = new Type<>(GET_INVENTORY_PAYLOAD_ID);
		public static final StreamCodec<RegistryFriendlyByteBuf, InventoryContentsS2CPayload> CODEC = StreamCodec.composite(
				BlockPos.STREAM_CODEC, InventoryContentsS2CPayload::pos,
				ItemStack.LIST_STREAM_CODEC, InventoryContentsS2CPayload::items,
				InventoryContentsS2CPayload::new);

		@NotNull
		@Override
		public Type<? extends CustomPacketPayload> type() {
			return ID;
		}
	}

	public static void onInitialize() {
		PayloadTypeRegistry.playC2S().register(GetInventoryC2SPayload.ID, GetInventoryC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(InventoryContentsS2CPayload.ID, InventoryContentsS2CPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(GetInventoryC2SPayload.ID,
			(payload, context) -> {
				BlockEntity block = context.player().level().getBlockEntity(payload.pos);

				if (!(block instanceof Container container)) {
					return;
				}

				List<ItemStack> items = new ArrayList<>();
				int size = container.getContainerSize();
				for (int i = 0; i < size; ++i) {
					ItemStack item = container.getItem(i);

					if (!item.isEmpty()) {
						items.add(item);
					}
				}

				InventoryContentsS2CPayload response = new InventoryContentsS2CPayload(payload.pos, items);
				ServerPlayNetworking.send(context.player(), response);
			});
	}

	public static void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(InventoryContentsS2CPayload.ID,
			(payload, context) -> {
				ClientLevel level = context.client().level;

				if (level == null) {
					return;
				}

				ClientEventHandler.cacheBlock(payload.pos, payload.items);
			});
	}

	@Override
	public void cacheInventory(BlockPos block) {
		GetInventoryC2SPayload payload = new GetInventoryC2SPayload(block);
		ClientPlayNetworking.send(payload);
	}
}

//? }
