package cn.liora.ui.widget.impl;

import cn.liora.Client;
import cn.liora.module.Module;
import cn.liora.module.impl.client.PostProcessing;
import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.ui.font.nano.NanoFontRenderer;
import cn.liora.util.render.nano.NanoUtil;
import cn.liora.ui.widget.Widget;
import cn.liora.util.animation.advanced.Direction;
import cn.liora.util.data.compare.CompareMode;
import cn.liora.util.data.compare.ModuleComparator;
import cn.liora.value.impl.ColorValue;
import cn.liora.value.impl.NumberValue;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.List;

/**
 * @author ChengFeng
 * @since 2024/8/1
 **/
public class ArraylistWidget extends Widget {
    public ArraylistWidget() {
        super("Arraylist", false);

        this.x = 1f;
        this.y = 0f;
    }

    private final NumberValue fontSize = new NumberValue("FontSize", 16f, 20f, 15f, 0.5f);
    private final ColorValue textColor = new ColorValue("TextColor", Color.WHITE);
    private final ColorValue backgroundColor = new ColorValue("BackgroundColor", new Color(0, 0, 0, 100));
    private final NumberValue indexOffset = new NumberValue("IndexOffset", 6f, 20f, 0f, 1f);

    @Override
    public void render() {
        NanoFontRenderer font = NanoFontLoader.misans;

        List<Module> moduleList = new java.util.ArrayList<>(Client.instance.moduleManager.moduleList.stream().toList());
        moduleList.sort(new ModuleComparator(CompareMode.Length, font));

        if (moduleList.isEmpty()) {
            this.width = 0;
            this.height = 0;
            return;
        }

        float currentFontSize = fontSize.getValue().floatValue();
        float yGap = font.getHeight(currentFontSize) + 6f;
        float moduleY = renderY + yGap / 2f;
        int index = 0;

        float maxWidth = 0f;
        for (Module m : moduleList) {
            float w = font.getStringWidth(m.name);
            if (w > maxWidth) maxWidth = w;
        }

        NanoUtil.scissorStart(renderX, renderY, maxWidth + 6f, sr.getScaledHeight() - renderY);

        for (Module module : moduleList) {
            float textWidth = font.getStringWidth(module.name) * fontSize.getValue().floatValue() / 15f;
            float animProgress = module.horizontalAnim.getOutput().floatValue(); // [0,1]

            float startX = renderX + 3f + maxWidth;
            float endX = renderX + 3f;
            float moduleX = startX + (endX - startX) * animProgress;

            float bgX = moduleX - 3f;
            float bgWidth = textWidth + 6f;
            float yTop = moduleY - yGap / 2f;

            NanoUtil.drawRect(bgX, yTop, bgWidth, yGap, backgroundColor.getValue(index));

            if (PostProcessing.bloom.getValue()) {
                font.drawGlowString(module.name, moduleX, moduleY, currentFontSize,
                        NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue(index));
            } else {
                font.drawString(module.name, moduleX, moduleY, currentFontSize,
                        NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue(index), true);
            }

            moduleY += module.verticalAnim.getOutput().floatValue() * yGap;

            if (!module.enabled) {
                if (module.horizontalAnim.getAnimation().finished(Direction.BACKWARDS)
                        && module.verticalAnim.getDirection() == Direction.FORWARDS) {
                    module.verticalAnim.changeDirection();
                }
            } else {
                if (module.verticalAnim.finished(Direction.FORWARDS)
                        && module.horizontalAnim.getDirection() == Direction.BACKWARDS) {
                    module.horizontalAnim.changeDirection();
                }
            }

            if (module.enabled) {
                index += indexOffset.getValue().intValue();
            }
        }

        NanoUtil.scissorEnd();

        this.width = maxWidth + 6f;
        this.height = moduleY - renderY;
    }
}
