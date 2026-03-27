package com.cyao.holoinventoryrevived.platform.forge;

//? if forge {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.network.GetInventoryC2SPayload;
import com.cyao.holoinventoryrevived.network.InventoryContentsS2CPayload;
import com.cyao.holoinventoryrevived.network.NetworkClient;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ForgeNetworkClient implements NetworkClient {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			//? >= 1.20.1
			ResourceLocation.fromNamespaceAndPath(HoloinventoryRevived.MOD_ID, "main"),
			//? <= 1.20
			//new ResourceLocation(HoloinventoryRevived.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	private static int id = 0;

	public static void register() {
		INSTANCE.registerMessage(id++,
				GetInventoryC2SPayload.class,
				GetInventoryC2SPayload::encode,
				GetInventoryC2SPayload::decode,
				GetInventoryC2SPayload::handle
		);

		INSTANCE.registerMessage(id++,
				InventoryContentsS2CPayload.class,
				InventoryContentsS2CPayload::encode,
				InventoryContentsS2CPayload::decode,
				InventoryContentsS2CPayload::handle
		);
	}

	@Override
	public void cacheInventory(BlockPos block) {
		GetInventoryC2SPayload payload = new GetInventoryC2SPayload(block);
		INSTANCE.sendToServer(payload);
	}
}

*///? }
