package com.cyao.holoinventoryrevived.config;

import com.cyao.holoinventoryrevived.HoloinventoryRevived;
import me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml.TomlWriter;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.IOException;

import static com.cyao.holoinventoryrevived.HoloinventoryRevived.CONFIG;
import static com.cyao.holoinventoryrevived.HoloinventoryRevived.CONFIG_FILE;

public class ConfigScreenProvider {

	public static Screen createConfigScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Component.translatable("title.holoinventory.config"));

		ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.holoinventory.general"));
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.holoinventory.control"), CONFIG.CONTROL_TRIGGER)
				.setTooltip(Component.translatable("option.holoinventory.control.details"))
				.setSaveConsumer(result -> CONFIG.CONTROL_TRIGGER = result)
				.build());

		general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.holoinventory.shift"), CONFIG.SHIFT_TRIGGER)
				.setTooltip(Component.translatable("option.holoinventory.shift.details"))
				.setSaveConsumer(result -> CONFIG.SHIFT_TRIGGER = result)
				.build());

		general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.holoinventory.constant"), CONFIG.ALWAYS_ACTIVE)
				.setTooltip(Component.translatable("option.holoinventory.constant.details"))
				.setSaveConsumer(result -> CONFIG.ALWAYS_ACTIVE = result)
				.build());

		builder.setSavingRunnable(() -> {
			TomlWriter tomlWriter = new TomlWriter();
			try {
				tomlWriter.write(CONFIG, CONFIG_FILE);
				HoloinventoryRevived.LOGGER.info("Successfully saved configuration to file");
			} catch (IOException e) {
				HoloinventoryRevived.LOGGER.info("Failed to save to config file: {}", e.toString());
			}
		});

		return builder.build();
	}
}
