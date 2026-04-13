package neb.nyx.event.render;

import net.minecraft.client.Camera;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class Render3DEvent {
    private static final Render3DEvent INSTANCE = new Render3DEvent();

    public Camera camera;
    // Please try all the Matrices first before thinking it doesn't work, because IDK what im doing.
    public Matrix4f matrix4f;  // View Matrix
    public Matrix4f matrix4f2; // Model Matrix
    public Matrix4f matrix4f3; // Projection Matrix
    public Vector4f vector4f;



    public static Render3DEvent get(Camera camera, Matrix4f matrix4f, Matrix4f matrix4f2, Matrix4f matrix4f3, Vector4f vector4f){
        INSTANCE.camera = camera;
        INSTANCE.matrix4f = matrix4f;
        INSTANCE.matrix4f2 = matrix4f2;
        INSTANCE.matrix4f3 = matrix4f3;
        INSTANCE.vector4f = vector4f;

        return INSTANCE;
    }
}
