package cn.liora.ui.clickgui2.component.value;

import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.util.render.nano.NanoUtil;
import cn.liora.value.impl.BoolValue;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class BoolValueButton extends ValueButton<Boolean> {

    public BoolValueButton(BoolValue value) {
        super(value);
    }

    @Override
    public void render() {
        NanoUtil.drawRect(x, y, width, height, new Color(40, 40, 40));
        NanoFontLoader.misans.bold().drawString(value.getName(), x + 5f, y + height / 2f, 12f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE);

        float switchWidth = 10f;
        float switchHeight = 10f;
        float switchX = x + width - switchWidth - 5f;
        float switchY = y + (height - switchHeight) / 2f;

        NanoUtil.drawRoundedRect(switchX, switchY, switchWidth, switchHeight, 5f, value.getValue() ? Color.GREEN : new Color(100, 100, 100));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovering && mouseButton == 0) {
            value.setValue(!value.getValue());
        }
    }
}