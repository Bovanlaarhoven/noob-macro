package org.bo.boclient.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.bo.boclient.utils.BlockDetection;

public class Farm {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final BlockDetection blockDetection = new BlockDetection();
    private boolean goingRight = true;
    private int tickCounter = 0;
    private boolean isRunning = false;

    public Farm() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void start() {
        tickCounter = 20;
        isRunning = true;
        toggleDirection(true);
        startMining();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
        KeyBinding.setKeyPressed(client.options.leftKey.getDefaultKey(), false);
        KeyBinding.setKeyPressed(client.options.rightKey.getDefaultKey(), false);
        stopMining();
    }

    private void onTick(MinecraftClient client) {
        if (!isRunning) return;

        if (tickCounter > 0) {
            tickCounter--;
        } else {
            if (blockDetection.isPlayerStandingStill()) {
                toggleDirection(false);
            } else {
                tickCounter = 20;
            }
        }
    }

    private void toggleDirection(boolean initialCall) {
        if (!isRunning || (!initialCall && !blockDetection.isPlayerStandingStill())) {
            return;
        }
        goingRight = !goingRight;
        if (goingRight) {
            KeyBinding.setKeyPressed(client.options.leftKey.getDefaultKey(), false);
            KeyBinding.setKeyPressed(client.options.rightKey.getDefaultKey(), true);
        } else {
            KeyBinding.setKeyPressed(client.options.rightKey.getDefaultKey(), false);
            KeyBinding.setKeyPressed(client.options.leftKey.getDefaultKey(), true);
        }
        tickCounter = 20;
    }

    public void startMining() {
        if (!isRunning) return;
        KeyBinding.setKeyPressed(client.options.attackKey.getDefaultKey(), true);
    }

    public void stopMining() {
        KeyBinding.setKeyPressed(client.options.attackKey.getDefaultKey(), false);
    }
}