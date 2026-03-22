package com.cyao.holoinventoryrevived.network;

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.event.ClientEventHandler;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;

import java.util.ArrayList;
import java.util.List;

//? if fabric {
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
//? } else if neoforge {
/*import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
*///? }

public class NetworkPayloadHandler {
	public static void getInventoryPayloadHandler(final NetworkPayloads.GetInventoryC2SPayload payload, Player player) {
		Level level = player.level();
		BlockEntity block = level.getBlockEntity(payload.pos());
		List<ItemStack> items = new ArrayList<>();

		String name = "";

		//? if (neoforge || forge) {
		/*IItemHandler handler = level.getCapability(
				Capabilities.ItemHandler.BLOCK,
				payload.pos(),
				null
		);
		*///? } else {
		Storage<ItemVariant> handler = ItemStorage.SIDED.find(level, payload.pos(), null);
		//? }

		if (block == null) {
			HoloinventoryRevived.LOGGER.debug("Received invalid request: {}", payload.pos());
			return;
		} else if (block instanceof EnderChestBlockEntity) {
			for (ItemStack item : player.getEnderChestInventory().getItems()) {
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
		} else if (handler != null) {
			//? if fabric {
			for (StorageView<ItemVariant> view : handler) {
				if (!view.isResourceBlank()) {
					ItemVariant variant = view.getResource();
					long amount = view.getAmount();

					if (amount != 0) {
						items.add(variant.toStack((int) amount));
					}
				}
			}
			//? } else {
			/*for (int i = 0; i < handler.getSlots(); i++) {
				ItemStack item = handler.getStackInSlot(i);
				if (!item.isEmpty()) {
					items.add(item);
				}
			}
			*///? }
		} else {
			HoloinventoryRevived.LOGGER.info("Failed to get inventory contents for block {}: Invalid type", payload.pos());
		}

		if (items.isEmpty()) {
			return;
		}

		if (name.isEmpty()) {
			if (block instanceof Nameable nameable) {
				name = nameable.getDisplayName().getString();
			} else {
				name = block.getBlockState().getBlock().getDescriptionId();
				String I18nName = I18n.get(name);
				String I18nNameAppended = I18n.get(name + ".name");
				if (!I18nName.equals(name)) {
					name = I18nName;
				} else if (!I18nNameAppended.equals(name)) {
					name = I18nNameAppended;
				}
			}
		}

		NetworkPayloads.InventoryContentsS2CPayload response =
				new NetworkPayloads.InventoryContentsS2CPayload(payload.pos(), items,
						name.replace("[", "").replace("]", ""));

		//? if fabric {
		ServerPlayNetworking.send((ServerPlayer) player, response);
		//? } else if neoforge {
		/*PacketDistributor.sendToPlayer((ServerPlayer) player, response);
		*///? }
	}

	public static void inventoryContentsPayloadHandler(final NetworkPayloads.InventoryContentsS2CPayload payload, Level level) {
		if (level == null) {
			return;
		}

		ClientEventHandler.cacheBlock(payload.pos(), payload.items(), payload.name());
	}
}
