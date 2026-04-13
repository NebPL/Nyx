package neb.nyx.util.input;


import com.mojang.blaze3d.platform.InputConstants;
import neb.nyx.Nyx;
import neb.nyx.event.EventHandler;
import neb.nyx.event.world.TickEvent;
import neb.nyx.gui.ClickGUI.ClickGui;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

public class KeyBindingManager {

    public KeyMapping openMenuKey;

    KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(Nyx.MOD_ID, "key_category")
    );

    public KeyBindingManager(){
        openMenuKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                    "key.nyx.openmenukey",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_RIGHT_SHIFT,
                    CATEGORY
                )
        );
    }

    @EventHandler
    public void tick(TickEvent.Pre event){
        if(openMenuKey.isDown()){
            Nyx.mc.setScreen(new ClickGui());
        }
    }
}
