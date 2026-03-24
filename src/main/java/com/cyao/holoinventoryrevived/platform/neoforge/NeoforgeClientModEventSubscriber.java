package com.cyao.holoinventoryrevived.platform.neoforge;

//? if neoforge {

import com.cyao.holoinventoryrevived.GlassesItem;
import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

//? <=1.21.2
//@EventBusSubscriber(modid = HoloinventoryRevived.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
//? > 1.21.2
@EventBusSubscriber(modid = HoloinventoryRevived.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientModEventSubscriber {
	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		HoloinventoryRevived.onInitializeClient();
	}

	@SubscribeEvent
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(GlassesItem.HOLO_GLASSES_ITEM);
		}
	}
}

//? }
