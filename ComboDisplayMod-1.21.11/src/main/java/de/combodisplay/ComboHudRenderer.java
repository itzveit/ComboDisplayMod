package de.combodisplay;

import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fStack;

/** Zeichnet den Combo-Text mittig oben mit Schatten und optionalem Pop-Effekt. */
public final class ComboHudRenderer {
    private static final Identifier LAYER_ID = Identifier.fromNamespaceAndPath("combo_display_mod", "combo_counter");

    private ComboHudRenderer() {}

    public static void register() {
        HudElementRegistry.attachElementAfter(IdentifiedLayer.MISC_OVERLAYS, LAYER_ID,
                (graphics, deltaTracker) -> render(graphics));
    }

    private static void render(GuiGraphics graphics) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui || !ComboConfig.enabled) return;

        int combo = ComboCounter.getCombo();
        if (combo < ComboConfig.minimumCombo) return;

        String label = combo == 2 ? "2x Combo" : combo + "er-Combo";
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int y = ComboConfig.positionY;
        int color = ComboConfig.color;
        float scale = ComboConfig.scale;

        // Kurzer Pop-Effekt nach dem letzten Angriff, ohne dauernde Animation pro Frame zu speichern.
        if (ComboConfig.hitAnimation) {
            float age = ComboCounter.getSecondsSinceLastAttack();
            if (age < 0.16f) {
                float progress = age / 0.16f;
                scale *= 1.0f + (float) Math.sin(progress * Math.PI) * 0.16f;
            }
        }

        int textWidth = client.font.width(label);
        float x = (screenWidth - textWidth * scale) / 2.0f;

        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y);
        graphics.pose().scale(scale, scale);
        graphics.drawString(client.font, Component.literal(label), 0, 0, color, true);
        graphics.pose().popMatrix();
    }
}
