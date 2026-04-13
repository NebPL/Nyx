package neb.nyx.modules;

import java.util.ArrayList;
import java.util.List;

public class Category{

    public String name;
    public ArrayList<Module> modules = new ArrayList<>();

    public Category(String name){
        this.name = name;
    }

    public void addModule(Module module){
        this.modules.add(module);
    }
}
