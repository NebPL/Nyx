package neb.nyx.modules.player;


import neb.nyx.event.EventHandler;
import neb.nyx.event.world.TickEvent;
import neb.nyx.modules.Categorys;
import neb.nyx.modules.Module;

public class Flight extends Module {
    public Flight() {
        super("Flight", Categorys.player, "This hack lets you Fly.");
    }

    @Override
    public void onEnable() {
        super.onEnable();

        System.out.println("ON");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        System.out.println("Off");
    }


    @EventHandler
    public void onUpdate(TickEvent.Pre event){
        //System.out.println("Update");
    }

}
