package neb.nyx.gui.ClickGUI;
import neb.nyx.modules.Module;

public class ModuleWidget {
    public CategoryWidget categoryWidget;
    public Module module;
    public int index;

    public ModuleWidget(CategoryWidget categoryWidget, Module module, int index){
        this.categoryWidget = categoryWidget;
        this.module = module;
        this.index = index;
    }

    public int getX(int PADDING_X){
        return categoryWidget.x + PADDING_X;
    }

    public int getY(int PADDING_Y){
        int boxHeight = categoryWidget.height + PADDING_Y * 2;
        return categoryWidget.y + (index + 1) * boxHeight;
    }

    public boolean isMouseCurserInWidgetBox(int PADDING_X,int PADDING_Y,double mouseX, double mouseY) {

        int x = this.getX(PADDING_X);
        int y = this.getY(PADDING_Y);

        return mouseX >= x
                && mouseY >= y
                && mouseX <= x + categoryWidget.width + PADDING_X * 2
                && mouseY <= y + categoryWidget.height + PADDING_Y * 2;
    }
}