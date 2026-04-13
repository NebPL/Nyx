package neb.nyx.event.render;

public class RenderCloseEvent {

    private static final RenderCloseEvent INSTANCE = new RenderCloseEvent();

    public static RenderCloseEvent get(){
        return INSTANCE;
    }
}
