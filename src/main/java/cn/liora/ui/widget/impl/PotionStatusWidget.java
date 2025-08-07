package cn.liora.ui.widget.impl;

import cn.liora.module.impl.client.PostProcessing;
import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.ui.font.nano.NanoFontRenderer;
import cn.liora.ui.widget.Widget;
import cn.liora.util.render.nano.NanoUtil;
import cn.liora.value.impl.ColorValue;
import cn.liora.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.Collection;

/**
 * @author LangYa466
 * @date 8/7/2025
 */
public class PotionStatusWidget extends Widget {
    private final NumberValue fontSize = new NumberValue("FontSize", 16f, 30f, 16f, 0.5f);
    private final ColorValue textColor = new ColorValue("TextColor", Color.WHITE);
    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", new Color(0, 0, 0, 120));
    private final NumberValue roundedRectRadius = new NumberValue("RoundedRect Radius", 2f, 10f, 0f, 0.5f);
    private final NumberValue size = new NumberValue("Size", 4f, 12f, 4f, 0.5f);  // 改名

    private final Minecraft mc = Minecraft.getMinecraft();

    public PotionStatusWidget() {
        super("PotionStatus", false);
        this.x = 2f;
        this.y = 40f;
    }

    private String formatTime(int ticks) {
        int seconds = ticks / 20;
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", mins, secs);
    }

    @Override
    public void render() {
        NanoFontRenderer font = NanoFontLoader.misans;
        float fs = fontSize.getValue().floatValue();
        float sz = size.getValue().floatValue();  // 改名

        Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
        if (effects.isEmpty()) {
            this.width = this.height = 0;
            return;
        }

        PotionEffect[] effectArray = effects.toArray(new PotionEffect[0]);

        float maxTextWidth = 0;
        float lineHeight = font.getHeight(fs);

        for (PotionEffect effect : effectArray) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String name = I18n.format(potion.getName());
            int amp = effect.getAmplifier();
            if (amp > 0) {
                String level = I18n.format("enchantment.level." + (amp + 1));
                name += " " + level;
            }
            String timeStr = formatTime(effect.getDuration());
            String fullLine = name + " " + timeStr;
            float lineWidth = font.getStringWidth(fullLine) * fs / 15f;
            if (lineWidth > maxTextWidth) maxTextWidth = lineWidth;
        }

        float totalWidth = sz * 2 + maxTextWidth;
        float totalHeight = lineHeight * effectArray.length + sz * 2;

        NanoUtil.drawRoundedRect(renderX, renderY, totalWidth, totalHeight,
                roundedRectRadius.getValue().floatValue(), backgroundColor.getValue());

        for (int i = 0; i < effectArray.length; i++) {
            PotionEffect effect = effectArray[i];
            Potion potion = Potion.potionTypes[effect.getPotionID()];

            float textX = renderX + sz;
            float textY = renderY + sz + i * lineHeight + lineHeight / 2f;

            String name = I18n.format(potion.getName());
            int amp = effect.getAmplifier();
            if (amp > 0) {
                String level = I18n.format("enchantment.level." + (amp + 1));
                name += " " + level;
            }
            String timeStr = formatTime(effect.getDuration());
            String fullText = name + " " + timeStr;

            if (PostProcessing.bloom.getValue()) {
                font.drawGlowString(fullText, textX, textY, fs,
                        NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue());
            } else {
                font.drawString(fullText, textX, textY, fs,
                        NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue(), true);
            }
        }

        this.width = totalWidth;
        this.height = totalHeight;
    }
}
