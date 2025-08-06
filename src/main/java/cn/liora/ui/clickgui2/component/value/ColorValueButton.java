package cn.liora.ui.clickgui2.component.value;

import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.util.render.nano.NanoUtil;
import cn.liora.value.impl.ColorValue;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class ColorValueButton extends ValueButton<Color> {

    public ColorValueButton(ColorValue value) {
        super(value);
    }

    @Override
    public void render() {
        NanoUtil.drawRect(x, y, width, height, new Color(40, 40, 40));

        NanoFontLoader.misans.bold().drawString(
                value.getName(),
                x + 5f,
                y + height / 2f,
                12f,
                NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE,
                Color.WHITE
        );

        float colorBoxSize = 10f;
        float colorBoxX = x + width - colorBoxSize - 5f;
        float colorBoxY = y + (height - colorBoxSize) / 2f;

        NanoUtil.drawRect(colorBoxX, colorBoxY, colorBoxSize, colorBoxSize, value.getValue());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovering && mouseButton == 0) {
            // Logic to open a color picker would go here.
        }
    }

    @Override
    public void update(float x, float y, float width, float height, int mouseX, int mouseY) {
        super.update(x, y, width, height, mouseX, mouseY);
    }
}