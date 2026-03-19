package com.cyao.holoinventoryrevived.platform.forge;

//? forge {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.config.ConfigScreenProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.BiFunction;

@Mod(HoloinventoryRevived.MOD_ID)
public class ForgeEntrypoint {
	public ForgeEntrypoint(FMLJavaModLoadingContext context) {
		if (FMLEnvironment.dist.isClient()) {
			context.getContainer().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
					() -> new ConfigScreenHandler.ConfigScreenFactory(
							(mc, screen) -> ConfigScreenProvider.createConfigScreen(screen)));
		}
		HoloinventoryRevived.onInitialize();
	}
}
*///?}
