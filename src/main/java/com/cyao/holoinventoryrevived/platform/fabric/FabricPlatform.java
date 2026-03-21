package com.cyao.holoinventoryrevived.platform.fabric;

//? fabric {

import com.cyao.holoinventoryrevived.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatform implements Platform {

	@Override
	public boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	@Override
	public ModLoader loader() {
		return ModLoader.FABRIC;
	}

	@Override
	public String mcVersion() {
		return FabricLoader.getInstance().getRawGameVersion();
	}

	@Override
	public Path getConfigDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	@Override
	public boolean isClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}
}
//?}
