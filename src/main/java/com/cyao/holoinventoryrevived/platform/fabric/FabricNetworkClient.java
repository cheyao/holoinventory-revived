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

	public record InventoryContentsS2CPayload(BlockPos pos, List<ItemStack> items, String name) implements CustomPacketPayload {
		public static final ResourceLocation GET_INVENTORY_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "inventory_contents");
		public static final Type<InventoryContentsS2CPayload> ID = new Type<>(GET_INVENTORY_PAYLOAD_ID);
		public static final StreamCodec<RegistryFriendlyByteBuf, InventoryContentsS2CPayload> CODEC = StreamCodec.composite(
				BlockPos.STREAM_CODEC, InventoryContentsS2CPayload::pos,
				ItemStack.LIST_STREAM_CODEC, InventoryContentsS2CPayload::items,
				ByteBufCodecs.STRING_UTF8, InventoryContentsS2CPayload::name,
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
				InventoryContentsS2CPayload response;

				BlockEntity block = context.player().level().getBlockEntity(payload.pos);
				List<ItemStack> items = new ArrayList<>();

				String name = "";

				if (block == null) {
					HoloinventoryRevived.LOGGER.debug("Received invalid request: {}", payload.pos);
					return;
				} else if (block instanceof EnderChestBlockEntity) {
					for (ItemStack item : context.player().getEnderChestInventory().items) {
						if (!item.isEmpty()) {
							items.add(item);
						}
					}

					name = Items.ENDER_CHEST.getDefaultInstance().getDisplayName().getString();
				} else if (block instanceof JukeboxBlockEntity jukebox) {
					if (!jukebox.getTheItem().isEmpty()) {
						items.add(jukebox.getTheItem());
					}

					name = Items.JUKEBOX.getDefaultInstance().getDisplayName().getString();
				} else if (block instanceof Container container) {
					for (int i = 0; i < container.getContainerSize(); ++i) {
						ItemStack item = container.getItem(i);

						if (!item.isEmpty()) {
							items.add(item);
						}
					}
				}

				if (items.isEmpty()) {
					return;
				}

				if (name == null || name.isEmpty()) {
					if (block instanceof Nameable nameable) {
						name = nameable.getDisplayName().getString();
					} else {
						name = block.getBlockState().getBlock().getName().toString();
					}
				}

				response = new InventoryContentsS2CPayload(payload.pos, items, name
						.replace("[", "").replace("]", ""));
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

				ClientEventHandler.cacheBlock(payload.pos, payload.items, payload.name);
			});
	}

	@Override
	public void cacheInventory(BlockPos block) {
		GetInventoryC2SPayload payload = new GetInventoryC2SPayload(block);
		ClientPlayNetworking.send(payload);
	}
}

//? }
