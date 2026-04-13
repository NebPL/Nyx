package neb.nyx.modules;



import neb.nyx.Nyx;

import java.util.ArrayList;
import java.util.List;

public class Module {

    public static List<Module> modules = new ArrayList<>();

    private String name;
    private Category ModuleCategory;
    private Boolean toggle;

    public Module(String name, Category category, String Description){
        this.name = name;
        this.ModuleCategory = category;
        this.toggle = false;
        category.addModule(this);
        modules.add(this);
    }

    public void toggle(){
        toggle = !toggle;
        if (toggle){
            Nyx.EventBus.register(this);
            onEnable();
        }else{
            Nyx.EventBus.unregister(this);
            onDisable();
        }
    }

    public void onEnable(){}
    public void onDisable(){}

    public String getName(){
        return name;
    }

    public Category getModuleCategory() {
        return ModuleCategory;
    }

    public boolean getState(){
        return toggle;
    }
}
