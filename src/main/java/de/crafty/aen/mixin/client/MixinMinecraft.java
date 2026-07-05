package de.crafty.aen.mixin.client;

import com.mojang.blaze3d.platform.WindowEventHandler;
import de.crafty.aen.config.AENConfigs;
import de.crafty.aen.config.AbstractAENConfig;
import de.crafty.aen.config.SlotConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft extends ReentrantBlockableEventLoop<Runnable> implements WindowEventHandler {

    public MixinMinecraft(String string) {
        super(string);
    }



    @Inject(method = "close", at = @At("HEAD"))
    private void saveConfig$0(CallbackInfo ci){
        AENConfigs.all().forEach(AbstractAENConfig::save);
    }

    @Redirect(method = "crash", at = @At(value = "INVOKE", target = "Ljava/lang/System;exit(I)V"))
    private static void saveConfig$1(int status){
        AENConfigs.all().forEach(AbstractAENConfig::save);
        System.exit(status);
    }
}
