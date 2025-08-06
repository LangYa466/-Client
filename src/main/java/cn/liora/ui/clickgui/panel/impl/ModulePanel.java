package cn.liora.ui.clickgui.panel.impl;

import cn.liora.Client;
import cn.liora.module.Module;
import cn.liora.ui.clickgui.ClickGUI;
import cn.liora.ui.clickgui.ThemeColor;
import cn.liora.ui.clickgui.component.impl.ButtonComponent;
import cn.liora.ui.clickgui.panel.Panel;
import cn.liora.ui.font.awt.AWTFontLoader;
import cn.liora.ui.font.awt.AWTAWTFontRenderer;
import cn.liora.util.animation.advanced.Direction;
import cn.liora.util.animation.advanced.composed.ColorAnimation;
import cn.liora.util.render.RenderUtil;
import cn.liora.util.render.RoundedUtil;
import cn.liora.value.Value;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class ModulePanel extends Panel {
    private final List<ValuePanel> valuePanelList;
    private final ButtonComponent enableBtn;
    public Module module;
    public boolean listening;
    private ColorAnimation colorAnim;

    public ModulePanel(Module module) {
        this.module = module;
        valuePanelList = new ArrayList<>();

        for (Value<?> value : module.valueList) {
            valuePanelList.add(new ValuePanel(value));
        }

        enableBtn = new ButtonComponent(module);
        height = 0f;
        listening = false;
        colorAnim = new ColorAnimation(Color.WHITE, ThemeColor.grayColor, 100);
    }

    @Override
    public void init() {
        width = (ClickGUI.width - ClickGUI.leftWidth - 30f) / 2f;
        valuePanelList.forEach(ValuePanel::init);
        enableBtn.init();
    }

    @Override
    public void draw(float x, float y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        AWTAWTFontRenderer font = AWTFontLoader.rubik(15);

        final float gap = 5f;
        float panelY = y + font.getFontHeight() + 3f;
        float contentX = x + 3f;
        float contentY = panelY + gap + 2f;

        boolean hovering = RenderUtil.hovering(mouseX, mouseY, x, y - 2f, width, font.getFontHeight() + 4f);

        if (hovering && colorAnim.getDirection() == Direction.FORWARDS) {
            colorAnim.changeDirection();
        } else if (!hovering && colorAnim.getDirection() == Direction.BACKWARDS) {
            colorAnim.changeDirection();
        }

        // Module name
        font.drawString(module.name, contentX, y, colorAnim.getOutput().getRGB());
        String keyText = listening? "Listening..." : hovering? "Click to bind" : "Key bind: " + (module.key == -1? "None" : Keyboard.getKeyName(module.key));
        font.drawString(keyText, contentX + width - 5f - font.getStringWidth(keyText), y, colorAnim.getOutput().getRGB());

        // Panel
        RoundedUtil.drawRoundOutline(x, panelY, width, height, 3f, 0.2f, ThemeColor.panelColor, ThemeColor.outlineColor);

        AWTAWTFontRenderer font2 = AWTFontLoader.greyCliff(16);
        font2.drawString("Enabled", contentX, contentY + 1f, Color.WHITE.getRGB());
        if (module.locked) {
            RenderUtil.drawImage(new ResourceLocation("liora/icon/lock.png"), contentX + width - 17f, contentY - 3f, 12f, 12f);
        } else enableBtn.draw(contentX, contentY, mouseX, mouseY);

        float valueY = contentY + enableBtn.height + gap;

        for (ValuePanel panel : valuePanelList) {
            panel.draw(contentX, valueY, mouseX, mouseY);
            if (panel.height != 0) valueY += panel.height + gap;
            if (valuePanelList.indexOf(panel) != valuePanelList.size() - 1) {
                RoundedUtil.drawRound(contentX, valueY - gap - 2.5f, width - 6f, 0.15f, 0f, ThemeColor.grayColor);
            }
        }

        height = valueY - panelY - gap;
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (RenderUtil.hovering(mouseX, mouseY, x, y - 2f, width, AWTFontLoader.rubik(15).getFontHeight() + 4f) && button == 0) {
            listening = true;
        }

        valuePanelList.forEach(it -> it.onMouseClick(mouseX, mouseY, button));
        enableBtn.onMouseClick(mouseX, mouseY, button);
        Client.instance.configManager.saveConfigs();
    }

    @Override
    public void onKeyTyped(char c, int keyCode) {
        if (listening) {
            module.key = keyCode == Keyboard.KEY_ESCAPE? -1 : keyCode;
            Client.instance.configManager.saveConfigs();
            return;
        }

        for (ValuePanel panel : valuePanelList) {
            panel.onKeyTyped(c, keyCode);
        }
    }

    @Override
    public void onMouseRelease() {
        valuePanelList.forEach(Panel::onMouseRelease);
    }
}
