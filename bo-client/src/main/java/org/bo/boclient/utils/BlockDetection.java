package org.bo.boclient.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class BlockDetection {
    MinecraftClient client = MinecraftClient.getInstance();

    public boolean isPlayerStandingStill() {
        if (client != null && client.player != null) {
            ClientPlayerEntity player = client.player;
            return Math.abs(player.getVelocity().x) < 0.01 && Math.abs(player.getVelocity().z) < 0.01;
        }
        return false;
    }
}