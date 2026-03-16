package com.example.modtemplate.platform.fabric;

//? fabric {

import com.example.modtemplate.HoloinventoryRevived;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		HoloinventoryRevived.onInitialize();
		FabricEventSubscriber.registerEvents();
	}
}
//?}
