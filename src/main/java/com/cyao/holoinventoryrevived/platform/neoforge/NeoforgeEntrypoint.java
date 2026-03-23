package com.cyao.holoinventoryrevived.platform.neoforge;

//? neoforge {

/*import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import com.cyao.holoinventoryrevived.config.ConfigScreenProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Objects;
import java.util.function.Supplier;

@Mod(HoloinventoryRevived.MOD_ID)
public class NeoforgeEntrypoint {
	public NeoforgeEntrypoint(ModContainer container, Dist dist) {
		if (dist.isClient()) {
			container.registerExtensionPoint(
					IConfigScreenFactory.class,
					(Supplier<IConfigScreenFactory>) () -> (modContainer, screen) -> ConfigScreenProvider.createConfigScreen(screen));
		}

		HoloinventoryRevived.onInitialize();

		HoloinventoryRevived.ITEMS.register(Objects.requireNonNull(container.getEventBus()));
		HoloinventoryRevived.ARMOR_MATERIALS.register(Objects.requireNonNull(container.getEventBus()));
	}
}
*///?}
