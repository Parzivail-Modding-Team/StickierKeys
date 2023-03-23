package com.parzivail.stickier.mixin;

import com.parzivail.stickier.StickierKeys;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin
{
	@Inject(method = "isAutoJumpEnabled()Z", at = @At("HEAD"), cancellable = true)
	public void isAutoJumpEnabled(CallbackInfoReturnable<Boolean> cir)
	{
		if (StickierKeys.mode != StickierKeys.Mode.Disabled)
			cir.setReturnValue(true);
	}

	@Redirect(method = "tickMovement()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
	public boolean isSprintPressed(KeyBinding instance)
	{
		if (StickierKeys.mode == StickierKeys.Mode.EnabledSprint)
			return true;

		return instance.isPressed();
	}
}
