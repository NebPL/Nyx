package neb.nyx.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import neb.nyx.Nyx;
import neb.nyx.event.render.Render3DEvent;
import neb.nyx.event.render.RenderCloseEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class GameRendererMixin {




    @Inject(
		method = "renderLevel",
		at = @At("RETURN"))
	private void onRender(GraphicsResourceAllocator graphicsResourceAllocator, DeltaTracker deltaTracker, boolean bl, Camera camera, Matrix4f matrix4f, Matrix4f matrix4f2, Matrix4f matrix4f3, GpuBufferSlice gpuBufferSlice, Vector4f vector4f, boolean bl2, CallbackInfo ci)
	{
        Nyx.EventBus.post(Render3DEvent.get(camera,matrix4f,matrix4f2,matrix4f3,vector4f));
	}

    @Inject(method = "close", at = @At("RETURN"))
    private void onGameRendererClose(CallbackInfo ci) {
        Nyx.EventBus.post(RenderCloseEvent.get());
        //RenderUtil.close();
    }
}



//    @Inject(
//        method = "renderLevel",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/client/renderer/LevelRenderer;addParticlesPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V",
//            shift = At.Shift.AFTER
//        )
//    )
//        private void onRenderLevel(
//        GraphicsResourceAllocator graphicsResourceAllocator,
//        DeltaTracker deltaTracker,
//        boolean bl,
//        Camera camera,
//        Matrix4f modelViewMatrix,
//        Matrix4f projMatrix,
//        Matrix4f matrix4f3,
//        GpuBufferSlice gpuBufferSlice,
//        Vector4f vector4f,
//        boolean bl2,
//        CallbackInfo ci
//    ) {
//        Minecraft mc = Minecraft.getInstance();
//        if (mc.level == null || mc.player == null) return;
//
//        // PoseStack mit Kamera-Rotation aufbauen
//        PoseStack poseStack = new PoseStack();
//        poseStack.last().pose().set(modelViewMatrix);
//
//        Vec3 camPos = camera.position();
//
//        RenderUtil ru = RenderUtil.getInstance();
//        ru.beginFrame();
//
//        // Block direkt unter dem Spieler – sofort beim Einloggen sichtbar
//        double px = Math.floor(mc.player.getX());
//        double py = Math.floor(mc.player.getY()) - 1; // Block unter den Füßen
//        double pz = Math.floor(mc.player.getZ());
//
//        // Kamera-Offset abziehen → relative Render-Koordinaten
//        double rx = px - camPos.x;
//        double ry = py - camPos.y;
//        double rz = pz - camPos.z;
//
//        // Rot durch Wände sichtbar
//        ru.addOutlineBoxThroughWalls(poseStack,
//            rx,     ry,     rz,
//            rx + 1, ry + 1, rz + 1,
//            1f, 0f, 0f, 0.6f
//        );
//
//        // Grün wenn direkt sichtbar
//        ru.addOutlineBox(poseStack,
//            rx,     ry,     rz,
//            rx + 1, ry + 1, rz + 1,
//            0f, 1f, 0f, 1f
//        );
//
//
//        ru.endFrame(mc, poseStack, Vec3.ZERO);
//    }
