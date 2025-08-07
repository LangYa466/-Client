package cn.liora.ui.widget.impl;

import cn.liora.module.impl.client.PostProcessing;
import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.ui.font.nano.NanoFontRenderer;
import cn.liora.ui.widget.Widget;
import cn.liora.util.render.nano.NanoUtil;
import cn.liora.value.impl.ColorValue;
import cn.liora.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

/**
 * @author LangYa466
 * @date 8/6/2025
 */
public class PingWidget extends Widget {
    private final NumberValue fontSize = new NumberValue("FontSize", 16f, 30f, 16f, 0.5f);
    private final ColorValue textColor = new ColorValue("TextColor", Color.WHITE);
    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", new Color(0, 0, 0, 120));
    private final NumberValue roundedRectRadius = new NumberValue("RoundedRect Radius", 2f, 10f, 0f, 0.5f);
    private final NumberValue padding = new NumberValue("Padding", 4f, 12f, 4f, 0.5f);

    public PingWidget() {
        super("PingWidget", false);
        this.x = 2f;
        this.y = 2f;
    }

    @Override
    public void render() {
        NanoFontRenderer font = NanoFontLoader.misans;
        float fs = fontSize.getValue().floatValue();
        float pad = padding.getValue().floatValue();

        String text =  (mc.getCurrentServerData() == null ? 0 : mc.getCurrentServerData().pingToServer) + " ms";
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
