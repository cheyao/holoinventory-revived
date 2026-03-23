package com.cyao.holoinventoryrevived.platform.neoforge;

//? if neoforge {

import com.cyao.holoinventoryrevived.network.NetworkClient;
import com.cyao.holoinventoryrevived.network.NetworkPayloads;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoforgeNetworkClient implements NetworkClient {
	@Override
	public void cacheInventory(BlockPos block) {
		NetworkPayloads.GetInventoryC2SPayload payload = new NetworkPayloads.GetInventoryC2SPayload(block);
		PacketDistributor.sendToServer(payload);
	}
}

//? }
