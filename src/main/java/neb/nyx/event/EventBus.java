package neb.nyx.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    public Map<Class<?>, ArrayList<Listener>> Listeners = new HashMap<>();

    public void register(Object obj){

        System.out.println("Object Registered: " + obj);

        for(Method method : obj.getClass().getDeclaredMethods()){
            if (method.isAnnotationPresent(EventHandler.class)){
                method.setAccessible(true);

                Class<?>[] params = method.getParameterTypes();
                if (params.length != 1) continue;

                Class<?>  eventType = params[0];

                Listeners
                        .computeIfAbsent(eventType, k -> new ArrayList<>())
                        .add(new Listener(obj, method));
            }
        }
    }

    public void unregister(Object obj){
        for (List<Listener> list : Listeners.values()) {
            list.removeIf(listener -> listener.owner.equals(obj));
        }
    }


    public void post(Object event){
        List<Listener> listeners = Listeners.get(event.getClass());
        if(listeners == null) return;

        // Kopie anlegen → schützt vor gleichzeitigen Änderungen
        for (Listener listener : new ArrayList<>(listeners)){
            try {
                listener.method.invoke(listener.owner, event);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
