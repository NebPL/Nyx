package neb.nyx.gui.util.widget;


import neb.nyx.modules.Category;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class CategoryWidget {
    public int x;
    public int y;
    public int width;
    public int height;
    public Category Category;
    public boolean showModules = true;
    public ArrayList<ModuleWidget> moduleWidgets = new ArrayList<>();


    public CategoryWidget(Category category, int x, int y){
        this.x = x;
        this.y = y;
        this.Category = category;
        width = Minecraft.getInstance().font.width(category.name);
        height = Minecraft.getInstance().font.lineHeight;
    }

    public boolean isMouseCurserInWidgetBox(int PADDINGX,int PADDINGY,double mouseX, double mouseY) {
        return mouseX >= x
                && mouseY >= y
                && mouseX <= x + width + PADDINGX * 2
                && mouseY <= y + height + PADDINGY * 2;
    }
}
