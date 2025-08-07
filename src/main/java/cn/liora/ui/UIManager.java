package cn.liora.ui;

import cn.liora.Client;
import cn.liora.event.api.EventTarget;
import cn.liora.event.impl.ChatGUIEvent;
import cn.liora.event.impl.NanoEvent;
import cn.liora.event.impl.ShaderEvent;
import cn.liora.event.type.EventPriority;
import cn.liora.ui.clickgui.ClickGUI;
import cn.liora.ui.clickgui2.ClickGui;
import cn.liora.ui.screen.main.FlatMainScreen;
import cn.liora.ui.widget.Widget;
import cn.liora.ui.widget.impl.*;
import cn.liora.util.MinecraftInstance;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengFeng
 * @since 2024/7/30
 **/
public class UIManager extends MinecraftInstance {
    public GuiScreen clickGUI;
    public GuiScreen mainScreen;

    public List<Widget> widgetList;

    public UIManager() {
        widgetList = new ArrayList<>();
    }

    public void registerWidgets() {
        register(new ArraylistWidget());
        register(new BPSWidget());
        register(new FPSWidget());
        register(new TimeWidget());
        register(new ComboWidget());
        register(new KeystrokeWidget());
        register(new PingWidget());
        register(new PotionStatusWidget());
    }

    public Widget getWidget(Class<? extends Widget> w) {
        for (Widget widget : widgetList) {
            if (widget.getClass() == w) return widget;
        }

        return null;
    }

    private void register(Widget widget) {
        this.widgetList.add(widget);
        Client.instance.moduleManager.register(widget);
    }

    public void initGUI() {
        mainScreen = new FlatMainScreen();

        Client.instance.eventBus.register(clickGUI);
    }

    @EventTarget(priority = EventPriority.HIGHEST)
    private void onNano(NanoEvent event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            return;
        }
        for (Widget widget : widgetList) {
            if (Client.instance.moduleManager.getModule(widget).enabled) {
                widget.updatePos();
                widget.render();
            }
        }
    }

    @EventTarget
    private void onShader(ShaderEvent event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            return;
        }
        for (Widget widget : widgetList) {
            if (Client.instance.moduleManager.getModule(widget).enabled) {
                widget.onShader(event);
            }
        }
    }

    @EventTarget
    private void onChatGUI(ChatGUIEvent event) {
        Widget draggingWidget = null;
        for (Widget widget : widgetList) {
            if (Client.instance.moduleManager.getModule(widget).enabled && widget.dragging) {
                draggingWidget = widget;
                break;
            }
        }

        for (Widget widget : widgetList) {
            if (Client.instance.moduleManager.getModule(widget).enabled) {
                widget.onChatGUI(event.mouseX, event.mouseY, (draggingWidget == null || draggingWidget == widget));
                if (widget.dragging) draggingWidget = widget;
            }
        }
    }
}
