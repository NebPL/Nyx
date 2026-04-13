package neb.nyx.util.render;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import neb.nyx.Nyx;
import neb.nyx.event.EventHandler;
import neb.nyx.event.render.RenderCloseEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import java.util.OptionalDouble;
import java.util.OptionalInt;


///Code was modified from the in world rendering example from [the Fabric Docs](https://docs.fabricmc.net/develop/rendering/world).
public class RenderUtil {


    public static RenderUtil INSTANCE;

    public RenderUtil(){
        if(INSTANCE != null){
            INSTANCE = this;
        }
    }

    public static RenderUtil getINSTANCE(){
        return INSTANCE;
    }



    private static final Vector4f COLOR_MODULATOR = new Vector4f(1f, 1f, 1f, 1f);
    private static final Vector3f MODEL_OFFSET = new Vector3f();
    private static final Matrix4f TEXTURE_MATRIX = new Matrix4f();

    private static final ByteBufferBuilder allocator = new ByteBufferBuilder(RenderType.BIG_BUFFER_SIZE);
//	private BufferBuilder buffer;
    private static MappableRingBuffer vertexBuffer;

    public static MultiBufferSource.BufferSource getVCP(){
        return Nyx.mc.renderBuffers().bufferSource();
    }

    public static void drawOutlinedBox(Matrix4f viewMatrix,Vec3 camera,AABB pos, int color) {
		PoseStack matrices = new PoseStack();

		matrices.pushPose();
        matrices.last().pose().set(viewMatrix);
		matrices.translate(-camera.x, -camera.y, -camera.z);


        RenderPipeline pipeline = pipeLine.LINES_THROUGH_WALLS;

        BufferBuilder buffer = new BufferBuilder(allocator,pipeline.getVertexFormatMode(),pipeline.getVertexFormat());



		renderWireframeBox(matrices.last().pose(), buffer,(float)pos.minX , (float) pos.minY, (float) pos.minZ, (float) pos.maxX, (float) pos.maxY, (float) pos.maxZ, 2f,color);
        drawFilledThroughWalls(buffer,Nyx.mc,pipeline);

		matrices.popPose();

	}

    public static void drawOutlinedBox(Matrix4f viewMatrix,Vec3 camera,AABB pos,float lineWidth, int color) {
		PoseStack matrices = new PoseStack();

		matrices.pushPose();
        matrices.last().pose().set(viewMatrix);
		matrices.translate(-camera.x, -camera.y, -camera.z);


        RenderPipeline pipeline = pipeLine.LINES_THROUGH_WALLS;

        BufferBuilder buffer = new BufferBuilder(allocator,pipeline.getVertexFormatMode(),pipeline.getVertexFormat());



		renderWireframeBox(matrices.last().pose(), buffer,(float)pos.minX , (float) pos.minY, (float) pos.minZ, (float) pos.maxX, (float) pos.maxY, (float) pos.maxZ, lineWidth,color);
        drawFilledThroughWalls(buffer,Nyx.mc,pipeline);

		matrices.popPose();

	}

    // Render the Wireframe Box
    private static void renderWireframeBox(Matrix4fc pose, BufferBuilder buffer,
        float minX, float minY, float minZ,
        float maxX, float maxY, float maxZ,float lineWidth,
        int color) {

        // Bottom face
        addLine(pose, buffer, minX, minY, minZ, maxX, minY, minZ, color,lineWidth);
        addLine(pose, buffer, maxX, minY, minZ, maxX, minY, maxZ, color,lineWidth);
        addLine(pose, buffer, maxX, minY, maxZ, minX, minY, maxZ, color,lineWidth);
        addLine(pose, buffer, minX, minY, maxZ, minX, minY, minZ, color,lineWidth);

        // Top face
        addLine(pose, buffer, minX, maxY, minZ, maxX, maxY, minZ, color,lineWidth);
        addLine(pose, buffer, maxX, maxY, minZ, maxX, maxY, maxZ, color,lineWidth);
        addLine(pose, buffer, maxX, maxY, maxZ, minX, maxY, maxZ, color,lineWidth);
        addLine(pose, buffer, minX, maxY, maxZ, minX, maxY, minZ, color,lineWidth);

        // Vertical edges
        addLine(pose, buffer, minX, minY, minZ, minX, maxY, minZ, color,lineWidth);
        addLine(pose, buffer, maxX, minY, minZ, maxX, maxY, minZ, color,lineWidth);
        addLine(pose, buffer, maxX, minY, maxZ, maxX, maxY, maxZ, color,lineWidth);
        addLine(pose, buffer, minX, minY, maxZ, minX, maxY, maxZ, color,lineWidth);
    }

    private static void addLine(Matrix4fc pose, BufferBuilder buffer,
                         float x1, float y1, float z1,
                         float x2, float y2, float z2,
                         int color, float lineWidth) {

        float dx = x2 - x1;
        float dy = y2 - y1;
        float dz = z2 - z1;
        float len = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
        if (len > 0) { dx /= len; dy /= len; dz /= len; }

        buffer.addVertex(pose, x1, y1, z1).setColor(color).setNormal(dx, dy, dz).setLineWidth(lineWidth);
        buffer.addVertex(pose, x2, y2, z2).setColor(color).setNormal(dx, dy, dz).setLineWidth(lineWidth);
    }


    public static void drawFilledBox(Matrix4f viewMatrix,Vec3 camera,AABB pos, int color) {

		PoseStack matrices = new PoseStack();

		matrices.pushPose();
        matrices.last().pose().set(viewMatrix);
		matrices.translate(-camera.x, -camera.y, -camera.z);


        RenderPipeline pipeline = pipeLine.FILLED_THROUGH_WALLS;

        BufferBuilder buffer = new BufferBuilder(allocator,pipeline.getVertexFormatMode(),pipeline.getVertexFormat());



		renderFilledBox(matrices.last().pose(), buffer,(float)pos.minX , (float) pos.minY, (float) pos.minZ, (float) pos.maxX, (float) pos.maxY, (float) pos.maxZ, color);
        drawFilledThroughWalls(buffer,Nyx.mc,pipeline);

		matrices.popPose();

	}

	private static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int color) {

		// Front Face
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(color);

		// Back face
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(color);

		// Left face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(color);

		// Right face
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(color);

		// Top face
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(color);

		// Bottom face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(color);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(color);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(color);
	}


    private static void drawFilledThroughWalls(BufferBuilder buffer,Minecraft client, @SuppressWarnings("SameParameterValue") RenderPipeline pipeline) {

        // Build the buffer
        MeshData builtBuffer = buffer.buildOrThrow();
        MeshData.DrawState drawParameters = builtBuffer.drawState();
        VertexFormat format = drawParameters.format();

        GpuBuffer vertices = upload(drawParameters, format, builtBuffer);

        draw(client, pipeline, builtBuffer, drawParameters, vertices, format);

        // Rotate the vertex buffer so we are less likely to use buffers that the GPU is using
        vertexBuffer.rotate();
    }

    private static GpuBuffer upload(MeshData.DrawState drawParameters, VertexFormat format, MeshData builtBuffer) {

        // Calculate the size needed for the vertex buffer
        int vertexBufferSize = drawParameters.vertexCount() * format.getVertexSize();

        // Initialize or resize the vertex buffer as needed
        if (vertexBuffer == null || vertexBuffer.size() < vertexBufferSize) {
            if (vertexBuffer != null) {
                vertexBuffer.close();
            }

            vertexBuffer = new MappableRingBuffer(() -> Nyx.MOD_ID + " example render pipeline", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_MAP_WRITE, vertexBufferSize);
        }

        // Copy vertex data into the vertex buffer
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

        try (GpuBuffer.MappedView mappedView = commandEncoder.mapBuffer(vertexBuffer.currentBuffer().slice(0, builtBuffer.vertexBuffer().remaining()), false, true)) {
            MemoryUtil.memCopy(builtBuffer.vertexBuffer(), mappedView.data());
        }

        return vertexBuffer.currentBuffer();
    }


    private static void draw(Minecraft client, RenderPipeline pipeline, MeshData builtBuffer, MeshData.DrawState drawParameters, GpuBuffer vertices, VertexFormat format) {

        GpuBuffer indices;
        VertexFormat.IndexType indexType;

        if (pipeline.getVertexFormatMode() == VertexFormat.Mode.QUADS) {
            // Sort the quads if there is translucency
            builtBuffer.sortQuads(allocator, RenderSystem.getProjectionType().vertexSorting());
            // Upload the index buffer
            indices = pipeline.getVertexFormat().uploadImmediateIndexBuffer(builtBuffer.indexBuffer());
            indexType = builtBuffer.drawState().indexType();
        } else {
            // Use the general shape index buffer for non-quad draw modes
            RenderSystem.AutoStorageIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getVertexFormatMode());
            indices = shapeIndexBuffer.getBuffer(drawParameters.indexCount());
            indexType = shapeIndexBuffer.type();
        }

        // Actually execute the draw
        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
                .writeTransform(RenderSystem.getModelViewMatrix(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);
        try (RenderPass renderPass = RenderSystem.getDevice()
                .createCommandEncoder()
                .createRenderPass(() -> Nyx.MOD_ID + " example render pipeline rendering", client.getMainRenderTarget().getColorTextureView(), OptionalInt.empty(), client.getMainRenderTarget().getDepthTextureView(), OptionalDouble.empty())) {
            renderPass.setPipeline(pipeline);

            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", dynamicTransforms);

            // Bind texture if applicable:
            // Sampler0 is used for texture inputs in vertices
            // renderPass.bindTexture("Sampler0", textureSetup.texure0(), textureSetup.sampler0());

            renderPass.setVertexBuffer(0, vertices);
            renderPass.setIndexBuffer(indices, indexType);

            // The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
            //noinspection ConstantValue
            renderPass.drawIndexed(0 / format.getVertexSize(), 0, drawParameters.indexCount(), 1);
        }

        builtBuffer.close();
    }



    @EventHandler
    public void close(RenderCloseEvent event) {
        System.out.println("Render Event");
        allocator.close();

        if (vertexBuffer != null) {
            vertexBuffer.close();
            vertexBuffer = null;
        }
    }
}