package com.cyao.holoinventoryrevived.platform.forge;

//? forge {

import com.cyao.holoinventoryrevived.GlassesItem;
import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.config.ConfigScreenProvider;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Objects;

@Mod(HoloinventoryRevived.MOD_ID)
public class ForgeEntrypoint {
	//? < 1.20.1
	public ForgeEntrypoint() {
	//? >= 1.20.1
	//public ForgeEntrypoint(FMLJavaModLoadingContext context) {
		if (FMLEnvironment.dist.isClient()) {
			//? < 1.20.1
			ModLoadingContext.get().getActiveContainer()
			//? >= 1.20.1
			//context.getContainer()
					.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
					() -> new ConfigScreenHandler.ConfigScreenFactory(
							(mc, screen) -> ConfigScreenProvider.createConfigScreen(screen)));
		}

		HoloinventoryRevived.onInitialize();

		//? < 1.20.1
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		//? >= 1.20.1
		//IEventBus modBus = context.getModEventBus();

		GlassesItem.ITEMS.register(Objects.requireNonNull(modBus));
		ForgeNetworkClient.register();
	}
}
//?}
