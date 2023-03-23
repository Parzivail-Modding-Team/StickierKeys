package com.parzivail.stickier.mixin;

import com.parzivail.stickier.StickierKeys;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin
{
	@ModifyArg(method = "tick(ZF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyboardInput;getMovementMultiplier(ZZ)F", ordinal = 0), index = 0)
	private boolean tick(boolean forwardPressed)
	{
		return StickierKeys.mode != StickierKeys.Mode.Disabled || forwardPressed;
	}
}
