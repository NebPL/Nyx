package neb.nyx;


import neb.nyx.event.EventBus;
import neb.nyx.modules.Categorys;
import neb.nyx.modules.Module;
import neb.nyx.modules.ModuleManager;
import neb.nyx.util.input.KeyBindingManager;
import neb.nyx.util.render.RenderUtil;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;

public class Nyx implements ModInitializer {


    public static final String MOD_ID = "nyx";
    public static Minecraft mc;
    public static EventBus EventBus = new EventBus();
    private final KeyBindingManager keyBindingManager = new KeyBindingManager();
    public static int MainColor = 0xFF000000;
    public static ModuleManager moduleManager;
    public static RenderUtil renderUtil = new RenderUtil();

    @Override
    public void onInitialize() {
        mc = Minecraft.getInstance();

        new Categorys();

        moduleManager = new ModuleManager();
        moduleManager.loadModules();
        moduleManager.getModules().forEach(Module::toggle);

        EventBus.register(keyBindingManager);
        EventBus.register(renderUtil);




    }
}
