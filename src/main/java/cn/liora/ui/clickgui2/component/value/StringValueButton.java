package cn.liora.ui.clickgui2.component.value;

import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.util.render.nano.NanoUtil;
import cn.liora.value.impl.StringValue;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class StringValueButton extends ValueButton<String> {

    public StringValueButton(StringValue value) {
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
        // TODO: Implement string input logic
    }
}