package neb.nyx.mixin;

import neb.nyx.Nyx;
import neb.nyx.event.world.TickEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void Pre_Tick(CallbackInfo ci){
        Nyx.EventBus.post(TickEvent.Pre.get());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void Post_Tick(CallbackInfo ci){
        Nyx.EventBus.post(TickEvent.Post.get());
    }
}
