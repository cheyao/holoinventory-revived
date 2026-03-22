package com.cyao.holoinventoryrevived.network;

import net.minecraft.core.BlockPos;

public interface NetworkClient {
	void cacheInventory(BlockPos block);
}
