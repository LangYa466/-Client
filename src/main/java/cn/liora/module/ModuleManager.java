package cn.liora.module;

import cn.liora.Client;
import cn.liora.event.api.EventTarget;
import cn.liora.event.impl.KeyEvent;
import cn.liora.module.impl.client.*;
import cn.liora.module.impl.movement.ToggleSprint;
import cn.liora.module.impl.render.*;
import cn.liora.ui.font.awt.AWTFont;
import cn.liora.ui.widget.Widget;
import cn.liora.util.data.compare.CompareMode;
import cn.liora.util.data.compare.ModuleComparator;
import cn.liora.util.exception.ModuleNotFoundException;
import cn.liora.util.exception.ValueLoadException;
import cn.liora.value.Value;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class ModuleManager {
    public List<Module> moduleList;

    public ModuleManager() {
        this.moduleList = new ArrayList<>();
    }

    /**
     * Register module
     *
     * @param module module
     */
    private void register(Module module) {
        register(module, module.getClass().getDeclaredFields());
    }

    /**
     * Register widget as a module
     *
     * @param widget widget
     */
    public void register(Widget widget) {
        Module widgetModule = new Module(widget.name, ModuleCategory.Widget);
        if (widget.defaultOn) widgetModule.toggle();
        this.moduleList.add(widgetModule);

        for (Field field : widget.getClass().getDeclaredFields()) {
            addValue(field, widgetModule, widget);
        }
    }

    private void addValue(Field field, Module module, Object obj) {
        field.setAccessible(true);
        if (field.getType().getSuperclass() == Value.class) {
            try {
                module.valueList.add((Value<?>) field.get(obj));
            } catch (IllegalAccessException e) {
                throw new ValueLoadException("Failed to load " + module.name + ", " + field.getName());
            }
        }
    }

    private void register(Module module, Field[] classFields) {
        this.moduleList.add(module);

        for (Field field : classFields) {
            addValue(field, module, module);
        }

        if (module.enabled) {
            Client.instance.eventBus.register(module);
        }
    }

    public void registerModules() {
        register(new ToggleSprint());
        register(new ClickGUIMod());
        register(new GUI());
        register(new PostProcessing());
        register(new Target());
        register(new FullBright());
        register(new NameTag());
        register(new Camera());
        register(new EntityCullingMod());
        register(new Animations());
    }

    public Module getModule(Class<? extends Module> klass) {
        for (Module module : moduleList) {
            if (klass == module.getClass()) return module;
        }

        throw new ModuleNotFoundException(klass.getName());
    }

    public List<Module> getModuleByCategory(ModuleCategory category) {
        List<Module> list = moduleList.stream()
                .filter(it -> it.category == category).collect(Collectors.toList());
        if (!list.isEmpty()) {
            list.sort(new ModuleComparator(CompareMode.Alphabet, (AWTFont) null));
        }
        return list;
    }

    public Module getModule(Widget widget) {
        for (Module module : moduleList) {
            if (widget.name.equals(module.name)) return module;
        }

        throw new ModuleNotFoundException(widget.name);
    }

    @EventTarget
    private void onKey(KeyEvent event) {
        moduleList.stream().filter(module -> module.key == event.key).forEach(Module::toggle);
    }
}
