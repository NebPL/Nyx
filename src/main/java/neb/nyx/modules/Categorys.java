package neb.nyx.modules;

import java.util.ArrayList;
import java.util.List;

public class Categorys {

    public static List<Category> Categorys = new ArrayList<>();

    public static final Category combat = new Category("combat");
    public static final Category donutsmp = new Category("donutsmp");
    public static final Category misc = new Category("misc");
    public static final Category player = new Category("player");
    public static final Category render = new Category("render");
    public static final Category world = new Category("world");

    public Categorys(){
        Categorys.add(combat);
        Categorys.add(donutsmp);
        Categorys.add(misc);
        Categorys.add(player);
        Categorys.add(render);
        Categorys.add(world);
    }
}
