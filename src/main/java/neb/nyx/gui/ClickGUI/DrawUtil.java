package neb.nyx.gui.ClickGUI;


import net.minecraft.client.gui.GuiGraphics;

public class DrawUtil {
    public static void DrawBorder(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int color){
        guiGraphics.hLine(x1,x2,y1,color);
        guiGraphics.hLine(x1,x2,y2,color);

        guiGraphics.vLine(x1,y1,y2,color);
        guiGraphics.vLine(x2,y1,y2,color);
    }
}
