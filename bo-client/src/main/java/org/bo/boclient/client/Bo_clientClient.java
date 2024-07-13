package org.bo.boclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.bo.boclient.modules.Farm;
import org.lwjgl.glfw.GLFW;

@Environment(net.fabricmc.api.EnvType.CLIENT)
public class Bo_clientClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    private Farm farm; // Declare the Farm instance at the class level

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.bo_client.toggleFarm",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.bo_client.misc"
        ));

        farm = new Farm(); // Initialize the Farm instance
        ClientTickEvents.END_CLIENT_TICK.register(client -> onTick());
    }



    public void onTick() {
        if (keyBinding.wasPressed()) {
            if (farm.isRunning()) {
                farm.stop();
            } else {
                farm.start();
            }
        }
    }
}