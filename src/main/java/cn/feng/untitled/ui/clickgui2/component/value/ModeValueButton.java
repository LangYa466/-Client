package cn.feng.untitled.ui.clickgui2.component.value;

import cn.feng.untitled.ui.font.nano.NanoFontLoader;
import cn.feng.untitled.util.render.nano.NanoUtil;
import cn.feng.untitled.value.impl.ModeValue;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class ModeValueButton extends ValueButton<String> {

    public ModeValueButton(ModeValue value) {
        super(value);
    }

    @Override
    public void render() {
        NanoUtil.drawRect(x, y, width, height, new Color(40, 40, 40));
        String text = value.getName() + ": " + value.getValue();
        NanoFontLoader.misans.bold().drawString(text, x + 5f, y + height / 2f, 12f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovering) {
            if (mouseButton == 0) {
                ((ModeValue) value).next();
            } else if (mouseButton == 1) {
                ((ModeValue) value).previous();
            }
        }
    }
}