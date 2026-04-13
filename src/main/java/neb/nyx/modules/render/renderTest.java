package neb.nyx.modules.render;

import neb.nyx.event.EventHandler;
import neb.nyx.event.render.Render3DEvent;
import neb.nyx.modules.Categorys;
import neb.nyx.modules.Module;
import neb.nyx.util.render.RenderUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class renderTest extends Module {
    public renderTest(){
        super("RenderTest", Categorys.misc,"This is just a render Test.");
    }

   @EventHandler
    public void onRender3d(Render3DEvent event) {
        AABB pos = new AABB(new Vec3(0,100,0),new Vec3(1,102,1));

        RenderUtil.drawFilledBox(event.matrix4f,event.camera.position(),pos,0x55FFFFFF);
        RenderUtil.drawOutlinedBox(event.matrix4f,event.camera.position(), pos,3f,0xFFFFFFFF);
    }

}
