package neb.nyx.event;

import java.lang.reflect.Method;

public class Listener {

    final Object owner;
    final Method method;

    Listener(Object owner, Method method){
        this.owner = owner;
        this.method = method;
    }
}
