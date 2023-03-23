package com.parzivail.stickier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StickierKeys implements ModInitializer
{
	public enum Mode
	{
		Disabled("stickierkeys.mode.disabled"),
		Enabled("stickierkeys.mode.enabled"),
		EnabledSprint("stickierkeys.mode.enabled_sprint");

		private final String key;

		Mode(String translationKey)
		{
			this.key = translationKey;
		}
	}

	public static final String MODID = "stickierkeys";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final KeyBinding KEY_TOGGLE = new KeyBinding("key.stickierkeys.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "key.category.stickierkeys");
	public static final KeyBinding KEY_TOGGLE_SPRINT = new KeyBinding("key.stickierkeys.toggle_sprint", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "key.category.stickierkeys");

	public static Mode mode = Mode.Disabled;

	@Override
	public void onInitialize()
	{
		KeyBindingHelper.registerKeyBinding(KEY_TOGGLE);
		KeyBindingHelper.registerKeyBinding(KEY_TOGGLE_SPRINT);

		ClientTickEvents.START_CLIENT_TICK.register(StickierKeys::handleKeys);
	}

	private static void handleKeys(MinecraftClient mc)
	{
		if (mc.player == null)
			return;

		var toggleWasPressed = KEY_TOGGLE.wasPressed();
		var toggleSprintWasPressed = KEY_TOGGLE_SPRINT.wasPressed();

		if (toggleWasPressed || toggleSprintWasPressed)
		{
			if (toggleSprintWasPressed)
			{
				if (mode == Mode.EnabledSprint)
					mode = Mode.Enabled;
				else
					mode = Mode.EnabledSprint;
			}
			else
			{
				if (mode != Mode.Disabled)
					mode = Mode.Disabled;
				else
					mode = Mode.Enabled;
			}

			mc.player.sendMessage(Text.translatable("stickierkeys.message.toggle", Text.translatable(mode.key).formatted(Formatting.BLUE)), true);
		}
	}
}
