package cn.feng.untitled.module.impl.client;

import cn.feng.untitled.Client;
import cn.feng.untitled.module.Module;
import cn.feng.untitled.module.ModuleCategory;
import cn.feng.untitled.ui.clickgui.ClickGUI;
import cn.feng.untitled.ui.clickgui2.ClickGui;
import cn.feng.untitled.value.impl.ModeValue;
import org.lwjgl.input.Keyboard;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class ClickGUIMod extends Module {
    public ClickGUIMod() {
        super("ClickGUI", ModuleCategory.Client, Keyboard.KEY_RSHIFT);
        locked = true;
    }

    private final ModeValue mode = new ModeValue("UIMode", "NL", "NL", "VapeTest") {
        @Override
        public void setValue(String value) {
            super.setValue(value);
            if (value.equalsIgnoreCase("VapeTest")) {
                Client.instance.uiManager.clickGUI = new ClickGui();
            } else {
                Client.instance.uiManager.clickGUI = new ClickGUI();
            }
        }
    };

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Client.instance.uiManager.clickGUI);
    }
}
