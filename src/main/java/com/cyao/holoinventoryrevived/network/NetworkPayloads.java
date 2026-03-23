package com.cyao.holoinventoryrevived.network;

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NetworkPayloads {
	public record GetInventoryC2SPayload(BlockPos pos) implements CustomPacketPayload {
		public static final ResourceLocation GET_INVENTORY_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "get_inventory");
		public static final CustomPacketPayload.Type<GetInventoryC2SPayload> ID = new Type<>(GET_INVENTORY_PAYLOAD_ID);
		public static final StreamCodec<RegistryFriendlyByteBuf, GetInventoryC2SPayload> CODEC = StreamCodec.composite(
				BlockPos.STREAM_CODEC, GetInventoryC2SPayload::pos,
				GetInventoryC2SPayload::new);

		@NotNull
		@Override
		public Type<? extends CustomPacketPayload> type() {
			return ID;
		}
	}

	public record InventoryContentsS2CPayload(BlockPos pos, List<ItemStack> items, String name) implements CustomPacketPayload {
		public static final ResourceLocation GET_INVENTORY_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "inventory_contents");
		public static final CustomPacketPayload.Type<InventoryContentsS2CPayload> ID = new Type<>(GET_INVENTORY_PAYLOAD_ID);
		//? if =1.21.1 {
		/*public static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStack>> ITEMSTACK_CODEC = ItemStack.LIST_STREAM_CODEC;
		*///? } else if >= 1.21.2 {
		public static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStack>> ITEMSTACK_CODEC = ItemStack.OPTIONAL_LIST_STREAM_CODEC;
		//? }
		public static final StreamCodec<RegistryFriendlyByteBuf, InventoryContentsS2CPayload> CODEC = StreamCodec.composite(
				BlockPos.STREAM_CODEC, InventoryContentsS2CPayload::pos,
				ITEMSTACK_CODEC, InventoryContentsS2CPayload::items,
				ByteBufCodecs.STRING_UTF8, InventoryContentsS2CPayload::name,
				InventoryContentsS2CPayload::new);

		@NotNull
		@Override
		public Type<? extends CustomPacketPayload> type() {
			return ID;
		}
	}
}
