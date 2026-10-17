package de.combodisplay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Lädt und speichert die Optionen in config/combo-display-mod.json. */
public final class ComboConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("combo-display-mod.json");

    public static boolean enabled = true;
    public static int minimumCombo = 2;
    public static int color = 0xFFFFFFFF;
    public static float scale = 1.0f;
    public static int positionY = 40;
    public static float resetSeconds = 3.0f;
    public static boolean resetOnDamage = true;
    public static boolean hitAnimation = true;

    private ComboConfig() {}

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        try {
            Values values = GSON.fromJson(Files.readString(FILE), Values.class);
            if (values != null) {
                enabled = values.enabled;
                minimumCombo = clamp(values.minimumCombo, 1, 5);
                color = values.color;
                scale = clamp(values.scale, 0.5f, 2.0f);
                positionY = clamp(values.positionY, 0, 200);
                resetSeconds = clamp(values.resetSeconds, 1.0f, 5.0f);
                resetOnDamage = values.resetOnDamage;
                hitAnimation = values.hitAnimation;
            }
        } catch (Exception exception) {
            System.err.println("[ComboDisplayMod] Konfiguration konnte nicht gelesen werden: " + exception.getMessage());
        }
    }

    public static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, GSON.toJson(new Values()));
        } catch (IOException exception) {
            System.err.println("[ComboDisplayMod] Konfiguration konnte nicht gespeichert werden: " + exception.getMessage());
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private static final class Values {
        boolean enabled = ComboConfig.enabled;
        int minimumCombo = ComboConfig.minimumCombo;
        int color = ComboConfig.color;
        float scale = ComboConfig.scale;
        int positionY = ComboConfig.positionY;
        float resetSeconds = ComboConfig.resetSeconds;
        boolean resetOnDamage = ComboConfig.resetOnDamage;
        boolean hitAnimation = ComboConfig.hitAnimation;
    }
}
