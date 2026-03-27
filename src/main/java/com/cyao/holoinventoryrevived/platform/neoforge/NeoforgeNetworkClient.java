package com.cyao.holoinventoryrevived.platform.neoforge;

//? if neoforge {

import com.cyao.holoinventoryrevived.network.NetworkClient;
import com.cyao.holoinventoryrevived.network.GetInventoryC2SPayload;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoforgeNetworkClient implements NetworkClient {
	@Override
	public void cacheInventory(BlockPos block) {
		GetInventoryC2SPayload payload = new GetInventoryC2SPayload(block);

		//? <=1.21.6
		//PacketDistributor.sendToServer(payload);
		//? >=1.21.7
		ClientPacketDistributor.sendToServer(payload);
	}
}

//? }
