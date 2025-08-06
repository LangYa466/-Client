package cn.feng.untitled.ui.widget.impl;

import cn.feng.untitled.module.impl.client.PostProcessing;
import cn.feng.untitled.ui.font.nano.NanoFontLoader;
import cn.feng.untitled.ui.font.nano.NanoFontRenderer;
import cn.feng.untitled.ui.widget.Widget;
import cn.feng.untitled.value.impl.ColorValue;
import cn.feng.untitled.value.impl.NumberValue;
import cn.feng.untitled.util.render.nano.NanoUtil;
import org.lwjgl.nanovg.NanoVG;
import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * @author LangYa466
 * @date 8/6/2025
 */
public class FPSWidget extends Widget {
    private final NumberValue fontSize = new NumberValue("FontSize", 16f, 30f, 16f, 0.5f);
    private final ColorValue textColor = new ColorValue("TextColor", Color.WHITE);
    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", new Color(0, 0, 0, 120));
    private final NumberValue roundedRectRadius = new NumberValue("RoundedRect Radius", 2f, 10f, 4f, 0.5f);
    private final NumberValue padding = new NumberValue("Padding", 4f, 12f, 4f, 0.5f);

    public FPSWidget() {
        super("FPSDisplay", false);
        this.x = 2f;
        this.y = 2f;
    }

    @Override
    public void render() {
        NanoFontRenderer font = NanoFontLoader.misans;
        float fs = fontSize.getValue().floatValue();
        float pad = padding.getValue().floatValue();

        int fps = Minecraft.getDebugFPS();

        String text = "FPS: " + fps;
        float lineHeight = font.getHeight(fs);
        float width = font.getStringWidth(text) * fs / 15f;
        float totalWidth = width + pad * 2;
        float totalHeight = lineHeight + pad * 2;

        NanoUtil.drawRoundedRect(renderX, renderY, totalWidth, totalHeight, roundedRectRadius.getValue().floatValue(), backgroundColor.getValue());

        float textX = renderX + pad;
        float textY = renderY + pad + lineHeight / 2f;

        if (PostProcessing.bloom.getValue()) {
            font.drawGlowString(text, textX, textY, fs,
                    NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue());
        } else {
            font.drawString(text, textX, textY, fs,
                    NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue(), true);
        }

        this.width = totalWidth;
        this.height = totalHeight;
    }
}
