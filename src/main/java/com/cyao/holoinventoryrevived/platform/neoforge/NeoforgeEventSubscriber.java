package com.cyao.holoinventoryrevived.platform.neoforge;

//? neoforge {

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import com.cyao.holoinventoryrevived.network.NetworkPayloadHandler;
import com.cyao.holoinventoryrevived.network.NetworkPayloads;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class NeoforgeEventSubscriber {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event) {
		if (HoloinventoryRevived.xplat().isClient()) {
			ClientEventHandler.onTick();
		}
	}

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar("1");

		registrar.playToServer(
				NetworkPayloads.GetInventoryC2SPayload.ID,
				NetworkPayloads.GetInventoryC2SPayload.CODEC,
				new MainThreadPayloadHandler<>(
						NeoforgeEventSubscriber::serverPayloadHandler
				)
			);

		//? if < 1.21.6 {
		registrar.playToClient(
				NetworkPayloads.InventoryContentsS2CPayload.ID,
				NetworkPayloads.InventoryContentsS2CPayload.CODEC,
				new MainThreadPayloadHandler<>(
						NeoforgeClientEventSubscriber::clientPayloadHandler
				)
		);
		//? }
	}

	private static void serverPayloadHandler(final NetworkPayloads.GetInventoryC2SPayload payload, final IPayloadContext context) {
		NetworkPayloadHandler.getInventoryPayloadHandler(payload, context.player());
	}
}
//?}
