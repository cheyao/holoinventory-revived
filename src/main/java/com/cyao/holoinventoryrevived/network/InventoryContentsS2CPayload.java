package com.cyao.holoinventoryrevived.network;

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

//? fabric
//import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

//? forge {
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
//? }

//? (<1.20.5 && fabric) || forge {
public class InventoryContentsS2CPayload {
	//? forge && >= 1.20.1
	//public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "inventory_contents");
	//? fabric
	//public static final ResourceLocation ID = new ResourceLocation(HoloinventoryRevived.MOD_ID, "inventory_contents");

	private BlockPos pos;
	private List<ItemStack> items;
	private String name;

	private InventoryContentsS2CPayload() {}
	public InventoryContentsS2CPayload(BlockPos blockPos, List<ItemStack> contents, String nname) {
		pos = blockPos;
		items = contents;
		name = nname;
	}

	public BlockPos pos() {
		return pos;
	}

	public List<ItemStack> items() {
		return items;
	}

	public String name() {
		return name;
	}

	//? fabric {
	/*public static FriendlyByteBuf encode(BlockPos pos, List<ItemStack> items, String name) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(pos);
		buf.writeInt(items.size());
		for (ItemStack item : items) {
			buf.writeItem(item);
		}
		buf.writeUtf(name);
		return buf;
	}
	*///? }

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeInt(items.size());
		for (ItemStack item : items) {
			buf.writeItem(item);
		}
		buf.writeUtf(name);
	}

	public static InventoryContentsS2CPayload decode(FriendlyByteBuf buf) {
		InventoryContentsS2CPayload payload = new InventoryContentsS2CPayload();

		payload.pos = buf.readBlockPos();
		int stackSize = buf.readInt();

		payload.items = new ArrayList<>();
		for (int i = 0; i < stackSize; ++i) {
			payload.items.add(buf.readItem());
		}

		payload.name = buf.readUtf();

		return payload;
	}

	//? forge {
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NetworkPayloadHandler.inventoryContentsPayloadHandler(this, net.minecraft.client.Minecraft.getInstance().level));
		});
		ctx.get().setPacketHandled(true);
	}
	//? }
}
//? } else {
/*import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record InventoryContentsS2CPayload(BlockPos pos, List<ItemStack> items, String name) implements CustomPacketPayload {
	public static final ResourceLocation GET_INVENTORY_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "inventory_contents");
	public static final CustomPacketPayload.Type<InventoryContentsS2CPayload> ID = new Type<>(GET_INVENTORY_PAYLOAD_ID);
	//? if =1.21.1
	//public static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStack>> ITEMSTACK_CODEC = ItemStack.LIST_STREAM_CODEC;
	//? if >= 1.21.2 || 1.21
	//public static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStack>> ITEMSTACK_CODEC = ItemStack.OPTIONAL_LIST_STREAM_CODEC;

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
*///? }
