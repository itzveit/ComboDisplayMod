package de.combodisplay;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Einstellungsmenü mit Cloth Config. */
public final class ComboConfigScreen {
    private ComboConfigScreen() {}

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("ComboDisplayMod"))
                .setSavingRunnable(ComboConfig::save);

        ConfigCategory category = builder.getOrCreateCategory(Component.literal("HUD"));
        ConfigEntryBuilder entries = builder.entryBuilder();

        category.addEntry(entries.startBooleanToggle(Component.literal("Mod aktiviert"), ComboConfig.enabled)
                .setDefaultValue(true).setSaveConsumer(value -> ComboConfig.enabled = value).build());

        category.addEntry(entries.startIntSlider(Component.literal("Minimale Combo"), ComboConfig.minimumCombo, 1, 5)
                .setDefaultValue(2).setSaveConsumer(value -> ComboConfig.minimumCombo = value).build());

        category.addEntry(entries.startColorField(Component.literal("Text-Farbe (ARGB)"), ComboConfig.color)
                .setDefaultValue(0xFFFFFFFF).setSaveConsumer(value -> ComboConfig.color = value).build());

        category.addEntry(entries.startFloatSlider(Component.literal("Skalierung"), ComboConfig.scale, 0.5f, 2.0f)
                .setDefaultValue(1.0f).setSaveConsumer(value -> ComboConfig.scale = value).build());

        category.addEntry(entries.startIntSlider(Component.literal("Position Y"), ComboConfig.positionY, 0, 200)
                .setDefaultValue(40).setSaveConsumer(value -> ComboConfig.positionY = value).build());

        category.addEntry(entries.startFloatSlider(Component.literal("Reset-Zeit (Sekunden)"), ComboConfig.resetSeconds, 1.0f, 5.0f)
                .setDefaultValue(3.0f).setSaveConsumer(value -> ComboConfig.resetSeconds = value).build());

        category.addEntry(entries.startBooleanToggle(Component.literal("Bei eigenem Schaden resetten"), ComboConfig.resetOnDamage)
                .setDefaultValue(true).setSaveConsumer(value -> ComboConfig.resetOnDamage = value).build());

        category.addEntry(entries.startBooleanToggle(Component.literal("Pop-Animation beim Hit"), ComboConfig.hitAnimation)
                .setDefaultValue(true).setSaveConsumer(value -> ComboConfig.hitAnimation = value).build());

        return builder.build();
    }
}
