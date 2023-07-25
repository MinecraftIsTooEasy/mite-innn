package net.xiaoyu233.mitemod.miteite.trans.fml;


import net.xiaoyu233.fml.FishModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishModLoader.class)
public class FishModLoaderMixin {

    @Inject(method = "versionCheck", at = @At("HEAD"), cancellable = true)
    private static void preventCheckVersion(CallbackInfoReturnable<String> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(null);
        callbackInfoReturnable.cancel();
    }
}
