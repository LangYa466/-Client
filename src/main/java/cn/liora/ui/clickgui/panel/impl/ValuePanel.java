package cn.liora.ui.clickgui.panel.impl;

import cn.liora.ui.clickgui.component.Component;
import cn.liora.ui.clickgui.component.impl.*;
import cn.liora.ui.clickgui.panel.Panel;
import cn.liora.ui.font.awt.AWTFontLoader;
import cn.liora.value.Value;
import cn.liora.value.impl.*;

import java.awt.*;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class ValuePanel extends Panel {
    public Value<?> value;
    private Component<?> component;

    public ValuePanel(Value<?> value) {
        this.value = value;

        if (value instanceof BoolValue bv) {
            component = new ButtonComponent(bv);
        } else if (value instanceof NumberValue nv) {
            component = new NumberComponent(nv);
        } else if (value instanceof ModeValue mv) {
            component = new ModeComponent(mv);
        } else if (value instanceof StringValue sv) {
            component = new StringComponent(sv);
        } else if (value instanceof ColorValue cv){
            component = new ColorComponent(cv);
        }
    }

    @Override
    public void init() {
        component.init();
    }

    @Override
    public void draw(float x, float y, int mouseX, int mouseY) {
        int textColor = Color.WHITE.getRGB();
        if (component instanceof ButtonComponent bc) {
            textColor = bc.textColAnim.getOutput().getRGB();
        }
        AWTFontLoader.greyCliff(16).drawString(value.getName(), x, y + 1, textColor);
        component.draw(x, y, mouseX, mouseY);
        height = component.height;
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        component.onMouseClick(mouseX, mouseY, button);
    }

    @Override
    public void onKeyTyped(char c, int keyCode) {
        component.onKeyTyped(c, keyCode);
    }

    @Override
    public void onMouseRelease() {
        component.onMouseRelease();
    }
}
