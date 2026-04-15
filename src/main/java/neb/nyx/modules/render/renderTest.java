package neb.nyx.modules.render;

import neb.nyx.event.EventHandler;
import neb.nyx.event.render.Render3DEvent;
import neb.nyx.modules.Categorys;
import neb.nyx.modules.Module;
import neb.nyx.util.render.RenderUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.UUID;

public class RenderTest extends Module {


    private final HashMap<UUID,Entity> entitiesToRender = new HashMap<>();

    public RenderTest(){
        super("RenderTest", Categorys.misc,"This is just a render Test.");
    }

   @EventHandler
    public void onRender3d(Render3DEvent event) {

       ServerEntityEvents.ENTITY_LOAD.register((entity, serverLevel) -> {

           if(entitiesToRender.get(entity) == null){
               entitiesToRender.put(entity.getUUID(),entity);
           }
       });

       ServerEntityEvents.ENTITY_UNLOAD.register((entity, serverLevel) -> {
           entitiesToRender.remove(entity.getUUID());
       });

        for (Entity entity : entitiesToRender.values()){
            AABB box = entity.getBoundingBox();

            RenderUtil.drawFilledBox(event.matrix4f,event.camera.position(),box,0x55FFFFFF);
            RenderUtil.drawOutlinedBox(event.matrix4f,event.camera.position(), box,3f,0xFFFFFFFF);
        }
    }
}
