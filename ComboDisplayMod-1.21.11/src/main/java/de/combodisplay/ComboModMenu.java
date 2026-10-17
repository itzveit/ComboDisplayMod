package de.combodisplay;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/** Bindet den Cloth-Config-Bildschirm an das Zahnrad in Mod Menu an. */
public final class ComboModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ComboConfigScreen::create;
    }
}
