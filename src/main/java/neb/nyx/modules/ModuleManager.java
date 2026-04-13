package neb.nyx.modules;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public void loadModules() {
        loadModulesFromPackage("neb.nyx.modules.combat");
        loadModulesFromPackage("neb.nyx.modules.misc");
        loadModulesFromPackage("neb.nyx.modules.player");
        loadModulesFromPackage("neb.nyx.modules.render");
        loadModulesFromPackage("neb.nyx.modules.world");
    }

    public void loadModulesFromPackage(String packageName) {
        String path = packageName.replace('.', '/');
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
            if (resource == null) {
                System.out.println("Package Name not found: " + packageName);
                return;
            }

            // Fix für URL-Encoding (Leerzeichen im Pfad etc.)
            File directory = new File(URI.create(resource.toString().replace(" ", "%20")));

            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("No directories: " + directory.getAbsolutePath());
                return;
            }

            File[] files = directory.listFiles();
            if (files == null) return;

            for (File file : files) {
                if (!file.getName().endsWith(".class")) continue;

                String className = packageName + "." + file.getName().replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(className, true,
                            Thread.currentThread().getContextClassLoader());

                    if (Module.class.isAssignableFrom(clazz)
                            && !clazz.isInterface()
                            ) {
                        Module module = (Module) clazz.getDeclaredConstructor().newInstance();
                        modules.add(module);
                        System.out.println("Loaded: " + module.getName());
                    }
                } catch (Exception e) {
                    System.out.println("Error with Class: " + className);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Module> getModules() {
        return modules;
    }
}
