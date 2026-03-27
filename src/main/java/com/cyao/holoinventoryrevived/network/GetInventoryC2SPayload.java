package com.cyao.holoinventoryrevived.network;

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

//? forge {
/*import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
*///? }

//? fabric
//import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

//? (<1.20.5 && fabric) || forge {
/*public class GetInventoryC2SPayload {
	//? forge && >=1.20.1
	//public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "get_inventory");
	//? fabric
	public static final ResourceLocation ID = new ResourceLocation(HoloinventoryRevived.MOD_ID, "get_inventory");

	private BlockPos pos;

	private GetInventoryC2SPayload() {}

	public GetInventoryC2SPayload(BlockPos block) {
		pos = block;
	}

	public BlockPos pos() {
		return pos;
	}

	//? fabric {
	public static FriendlyByteBuf encode(BlockPos pos) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(pos);
		return buf;
	}
	//? }

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
	}

	public static GetInventoryC2SPayload decode(FriendlyByteBuf buf) {
		GetInventoryC2SPayload payload = new GetInventoryC2SPayload();
		payload.pos = buf.readBlockPos();
		return payload;
	}

	//? forge {
	/^public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player == null) return;
			NetworkPayloadHandler.getInventoryPayloadHandler(this, player);
		});
		ctx.get().setPacketHandled(true);
	}
	^///? }
}
*///? } else {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import org.jetbrains.annotations.NotNull;

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
//? }
