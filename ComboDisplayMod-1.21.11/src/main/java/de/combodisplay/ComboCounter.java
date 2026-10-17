package de.combodisplay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

/** Verwalten des Zählers und des zeitgesteuerten Zurücksetzens. */
public final class ComboCounter {
    private static int combo;
    private static long lastAttackNanos;
    private static int previousHurtTime;
    private static long lastHitNanos;

    private ComboCounter() {}

    public static void onAttackAttempt() {
        if (!ComboConfig.enabled) return;

        long now = System.nanoTime();
        // Mehrere Angriffe innerhalb des Zeitfensters bilden eine Combo.
        combo++;
        lastAttackNanos = now;
        lastHitNanos = now;
    }

    public static void tick(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null) {
            reset();
            previousHurtTime = 0;
            return;
        }

        // HurtTime steigt beim erkannten eigenen Schaden. Das ist clientseitig
        // und benötigt keine zusätzlichen Netzwerkpakete.
        int hurtTime = player.hurtTime;
        if (ComboConfig.resetOnDamage && hurtTime > previousHurtTime && hurtTime > 0) {
            reset();
        }
        previousHurtTime = hurtTime;

        if (combo > 0 && lastAttackNanos != 0L) {
            long timeout = (long) (ComboConfig.resetSeconds * 1_000_000_000L);
            if (System.nanoTime() - lastAttackNanos >= timeout) {
                reset();
            }
        }
    }

    public static void reset() {
        combo = 0;
        lastAttackNanos = 0L;
        lastHitNanos = 0L;
    }

    public static int getCombo() {
        return combo;
    }

    /** Sekunden seit dem letzten Angriffsereignis, für die Animation. */
    public static float getSecondsSinceLastAttack() {
        if (lastHitNanos == 0L) return 100f;
        return (System.nanoTime() - lastHitNanos) / 1_000_000_000.0f;
    }
}
