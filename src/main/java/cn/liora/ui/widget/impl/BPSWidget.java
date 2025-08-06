package cn.liora.ui.widget.impl;

import cn.liora.module.impl.client.PostProcessing;
import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.ui.font.nano.NanoFontRenderer;
import cn.liora.ui.widget.Widget;
import cn.liora.value.impl.ColorValue;
import cn.liora.value.impl.NumberValue;
import cn.liora.util.render.nano.NanoUtil;
import org.lwjgl.nanovg.NanoVG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.awt.*;

/**
 * @author LangYa466
 * @date 8/6/2025
 */
public class BPSWidget extends Widget {
    private final NumberValue fontSize = new NumberValue("FontSize", 16f, 30f, 16f, 0.5f);
    private final ColorValue textColor = new ColorValue("TextColor", Color.WHITE);
    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", new Color(0, 0, 0, 120));
    private final NumberValue roundedRectRadius = new NumberValue("RoundedRect Radius", 2f, 10f, 4f, 0.5f);
    private final NumberValue padding = new NumberValue("Padding", 4f, 12f, 4f, 0.5f);
    private final NumberValue smoothing = new NumberValue("Smoothing", 10f, 60f, 15f, 1f); // 平滑参数

    private double lastPosX = Double.NaN;
    private double lastPosZ = Double.NaN;
    private long lastTime = -1;
    private double smoothedBPS = 0;

    public BPSWidget() {
        super("BPSDisplay", false);
        this.x = 2f;
        this.y = 18f; // 放在 FPS 下面
    }

    private double computeBPS() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (player == null) return 0;

        double currentX = player.posX;
        double currentZ = player.posZ;
        long now = System.currentTimeMillis();

        if (Double.isNaN(lastPosX) || lastTime < 0) {
            lastPosX = currentX;
            lastPosZ = currentZ;
            lastTime = now;
            return 0;
        }

        double dx = currentX - lastPosX;
        double dz = currentZ - lastPosZ;
        double dist = Math.sqrt(dx * dx + dz * dz);
        double deltaSec = (now - lastTime) / 1000.0;
        lastPosX = currentX;
        lastPosZ = currentZ;
        lastTime = now;

        if (deltaSec <= 0) return 0;

        double instant = dist / deltaSec;
        double alpha = 1.0 / Math.max(1.0, smoothing.getValue());
        smoothedBPS = smoothedBPS == 0 ? instant : smoothedBPS * (1 - alpha) + instant * alpha;
        return smoothedBPS;
    }

    @Override
    public void render() {
        NanoFontRenderer font = NanoFontLoader.misans;
        float fs = fontSize.getValue().floatValue();
        float pad = padding.getValue().floatValue();

        double bps = computeBPS();
        String text = String.format("BPS: %.2f", bps);

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
