package de.combodisplay;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

/** Haupteinstiegspunkt des clientseitigen Mods. */
public final class ComboDisplayMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ComboConfig.load();

        // Dieser Callback erkennt einen clientseitigen Angriffsversuch auf ein Entity.
        // Minecraft stellt hier nicht zuverlässig fest, ob der Server den Treffer bestätigt.
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient && player instanceof PlayerEntity) {
                ComboCounter.onAttackAttempt();
            }
            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> ComboCounter.tick(client));
        ComboHudRenderer.register();
    }
}
