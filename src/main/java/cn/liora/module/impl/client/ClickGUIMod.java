package cn.liora.module.impl.client;

import cn.liora.Client;
import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import cn.liora.ui.clickgui.ClickGUI;
import cn.liora.ui.clickgui2.ClickGui;
import cn.liora.value.impl.ModeValue;
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
