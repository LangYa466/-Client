package cn.liora.ui.clickgui2.component.value;

import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.util.render.nano.NanoUtil;
import cn.liora.value.impl.NumberValue;
import org.lwjgl.input.Mouse;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class NumberValueButton extends ValueButton<Double> {
    private boolean dragging = false;

    public NumberValueButton(NumberValue value) {
        super(value);
    }

    @Override
    public void render() {
        NanoUtil.drawRect(x, y, width, height, new Color(40, 40, 40));
        double value = this.value.getValue();
        double min = ((NumberValue) this.value).minimum;
        double max = ((NumberValue) this.value).maximum;
        double percent = (value - min) / (max - min);

        NanoUtil.drawRect(x, y, (float) (width * percent), height, new Color(70, 70, 70));

        String text = this.value.getName() + ": " + String.format("%.2f", value);
        NanoFontLoader.misans.bold().drawString(text, x + 5f, y + height / 2f, 12f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE);
    }

    @Override
    public void update(float x, float y, float width, float height, int mouseX, int mouseY) {
        super.update(x, y, width, height, mouseX, mouseY);
        if (dragging) {
            if (!Mouse.isButtonDown(0)) {
                dragging = false;
                return;
            }

            NumberValue numberValue = (NumberValue) this.value;
            double min = numberValue.minimum;
            double max = numberValue.maximum;
            double step = numberValue.step;

            double diff = Math.max(min, Math.min(max, min + (max - min) * ((mouseX - this.x) / this.width)));
            double val = Math.round(diff / step) * step;
            numberValue.setValue(val);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovering && mouseButton == 0) {
            dragging = true;
        }
    }
}