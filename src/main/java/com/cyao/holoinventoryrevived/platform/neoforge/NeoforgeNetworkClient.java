package com.cyao.holoinventoryrevived.platform.neoforge;

//? if neoforge {

import com.cyao.holoinventoryrevived.network.NetworkClient;
import com.cyao.holoinventoryrevived.network.GetInventoryC2SPayload;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoforgeNetworkClient implements NetworkClient {
	@Override
	public void cacheInventory(BlockPos block) {
		GetInventoryC2SPayload payload = new GetInventoryC2SPayload(block);
		PacketDistributor.sendToServer(payload);
	}
}

//? }
