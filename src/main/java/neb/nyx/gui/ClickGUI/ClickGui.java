package neb.nyx.gui.ClickGUI;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import neb.nyx.Nyx;
import neb.nyx.modules.Category;
import neb.nyx.modules.Categorys;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ClickGui extends Screen {

    public ArrayList<CategoryWidget> CategoryWidgets = new ArrayList<>();

    private static final int PADDING_X = 10;
    private static final int PADDING_Y = 6;
    private static final int SPACING = 8;

    CategoryWidget draggedCategoryWidget = null;

    boolean mouseDragging = false;
    boolean mouseLeftClicked = false;
    boolean mouseRightClicked = false;


    public ClickGui() {
        super(Component.nullToEmpty("Nyx Click GUI"));

        int x = 10;
        int y = 10;
        int offset_x = 0;

        for (Category category : Categorys.Categorys) {
            CategoryWidget categoryWidget = new CategoryWidget(category, x + offset_x, y);

            int[] i = {0};
            category.modules.forEach(module -> {
                categoryWidget.moduleWidgets.add(new ModuleWidget(categoryWidget, module, i[0]));
                i[0]++;
            });

            CategoryWidgets.add(categoryWidget);

            int widgetWidth = categoryWidget.width + PADDING_X * 2;
            offset_x += widgetWidth + SPACING;
        }
    }


    @Override
    public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double d, double e) {

        if (mouseLeftClicked && mouseDragging && draggedCategoryWidget != null) {
            draggedCategoryWidget.x += (int) d;
            draggedCategoryWidget.y += (int) e;
        }
        return super.mouseDragged(mouseButtonEvent, d, e);
    }



    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {

        if (mouseButtonEvent.button() == GLFW.GLFW_MOUSE_BUTTON_1) {
            for (CategoryWidget categoryWidget : CategoryWidgets) {
                if (categoryWidget.isMouseCurserInWidgetBox(PADDING_X, PADDING_Y, mouseButtonEvent.x(), mouseButtonEvent.y())) {
                    draggedCategoryWidget = categoryWidget;
                    mouseLeftClicked = true;
                    mouseRightClicked = false;
                    mouseDragging = true;
                }

                for (ModuleWidget moduleWidget : categoryWidget.moduleWidgets){
                    if (moduleWidget.isMouseCurserInWidgetBox(PADDING_X,PADDING_Y,mouseButtonEvent.x(), mouseButtonEvent.y())){
                        moduleWidget.module.toggle();

                    }
                }
            }
        }

        if (mouseButtonEvent.button() == GLFW.GLFW_MOUSE_BUTTON_2) {
            for (CategoryWidget categoryWidget : CategoryWidgets) {
                if (categoryWidget.isMouseCurserInWidgetBox(PADDING_X, PADDING_Y, mouseButtonEvent.x(), mouseButtonEvent.y())) {
                    draggedCategoryWidget = categoryWidget;
                    mouseRightClicked = true;
                    mouseLeftClicked = false;
                    categoryWidget.showModules = !categoryWidget.showModules;
                }
            }
        }
        return super.mouseClicked(mouseButtonEvent, bl);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {

        if (mouseButtonEvent.button() == GLFW.GLFW_MOUSE_BUTTON_1) {
            draggedCategoryWidget = null;
            mouseRightClicked = false;
            mouseDragging = false;
            mouseLeftClicked = false;
        }

        if (mouseButtonEvent.button() == GLFW.GLFW_MOUSE_BUTTON_3) {
            draggedCategoryWidget = null;
            mouseRightClicked = false;
        }
        return super.mouseReleased(mouseButtonEvent);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);



        for (CategoryWidget categoryWidget : CategoryWidgets) {
            int boxX1 = categoryWidget.x;
            int boxY1 = categoryWidget.y;
            int boxX2 = categoryWidget.x + categoryWidget.width + PADDING_X * 2;
            int boxY2 = categoryWidget.y + categoryWidget.height + PADDING_Y * 2;

            DrawUtil.DrawBorder(guiGraphics, boxX1, boxY1, boxX2, boxY2, 0xFFFFFFFF);

            guiGraphics.drawString(Nyx.mc.font,
                    categoryWidget.Category.name,
                    boxX1 + PADDING_X,
                    boxY1 + PADDING_Y,
                    0xFFFFFFFF,
                    false
            );

            if (categoryWidget.showModules && categoryWidget.moduleWidgets != null) {
                for (ModuleWidget moduleWidget : categoryWidget.moduleWidgets) {
                    int moduleY1 = moduleWidget.getY(PADDING_Y);
                    int moduleY2 = moduleY1 + categoryWidget.height + PADDING_Y * 2;


                    if(moduleWidget.module.getState()){
                        guiGraphics.fill(boxX1, moduleY1, boxX2, moduleY2,0x7700FF00);
                    }


                    DrawUtil.DrawBorder(guiGraphics, boxX1, moduleY1, boxX2, moduleY2, 0xFFFFFFFF);

                    guiGraphics.drawString(Nyx.mc.font,
                            moduleWidget.module.getName(),
                            boxX1 + PADDING_X,
                            moduleY1 + PADDING_Y,
                            0xFFFFFFFF,
                            false);
                }
            }
        }
    }

}