package cn.liora.ui.widget.impl;

import cn.liora.module.impl.client.PostProcessing;
import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.ui.font.nano.NanoFontRenderer;
import cn.liora.ui.widget.Widget;
import cn.liora.value.impl.BoolValue;
import cn.liora.value.impl.ColorValue;
import cn.liora.value.impl.NumberValue;
import cn.liora.util.render.nano.NanoUtil;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LangYa466
 * @date 8/7/2025
 */
public class KeystrokeWidget extends Widget {
    private final NumberValue fontSize = new NumberValue("Font Size", 16f, 30f, 16f, 0.5f);
    private final NumberValue size = new NumberValue("Size", 6f, 16f, 8f, 0.5f);
    private final NumberValue spacing = new NumberValue("Spacing", 2f, 8f, 4f, 0.5f);
    private final NumberValue radius = new NumberValue("RoundedRect Radius", 2f, 10f, 0f, 0.5f);

    private final ColorValue background = new ColorValue("Background", new Color(0, 0, 0, 120));
    private final ColorValue pressedColor = new ColorValue("Pressed Color", new Color(255, 255, 255, 80));
    private final ColorValue textColor = new ColorValue("Text Color", Color.WHITE);

    private final BoolValue showSpace = new BoolValue("Show Space", false);

    private final Map<String, Integer> keyMap = new LinkedHashMap<>();

    public KeystrokeWidget() {
        super("Keystrokes", false);
        this.x = 2f;
        this.y = 40f;

        keyMap.put("W", Keyboard.KEY_W);
        keyMap.put("A", Keyboard.KEY_A);
        keyMap.put("S", Keyboard.KEY_S);
        keyMap.put("D", Keyboard.KEY_D);
        keyMap.put("SPACE", Keyboard.KEY_SPACE);
    }

    @Override
    public void render() {
        NanoFontRenderer font = NanoFontLoader.misans;
        float fs = fontSize.getValue().floatValue();
        float sz = size.getValue().floatValue();
        float gap = spacing.getValue().floatValue();
        float round = radius.getValue().floatValue();

        float boxHeight = font.getHeight(fs) + sz * 2;
        float boxWidth = 0;
        for (String key : new String[]{"W", "A", "S", "D"}) {
            float w = font.getStringWidth(key) * fs / 15f + sz * 2;
            boxWidth = Math.max(boxWidth, w);
        }

        // 3普通键宽度加2间距宽度
        float spaceKeyWidth = boxWidth * 3 + gap * 2;

        float totalWidth = boxWidth * 3 + gap * 2;
        if (showSpace.getValue()) {
            totalWidth = Math.max(totalWidth, spaceKeyWidth);
        }

        int rowCount = showSpace.getValue() ? 3 : 2;
        float totalHeight = boxHeight * rowCount + gap * (rowCount - 1);

        float centerX = renderX + (totalWidth - (boxWidth * 3 + gap * 2)) / 2f;
        float currentY = renderY;

        // 第一排 W 居中显示
        drawKey("W", renderX + (totalWidth - boxWidth) / 2f, currentY, boxWidth, boxHeight, font, fs, sz, round);
        currentY += boxHeight + gap;

        // 第二排 A S D
        drawKey("A", centerX, currentY, boxWidth, boxHeight, font, fs, sz, round);
        drawKey("S", centerX + boxWidth + gap, currentY, boxWidth, boxHeight, font, fs, sz, round);
        drawKey("D", centerX + (boxWidth + gap) * 2, currentY, boxWidth, boxHeight, font, fs, sz, round);
        currentY += boxHeight + gap;

        // 第三排 SPACE
        if (showSpace.getValue()) {
            drawKey("SPACE", centerX, currentY, spaceKeyWidth, boxHeight, font, fs, sz, round);
        }

        this.width = totalWidth;
        this.height = totalHeight;
    }

    private void drawKey(String keyName, float x, float y, float w, float h, NanoFontRenderer font, float fs, float sz, float radius) {
        int keyCode = keyMap.get(keyName);
        boolean pressed = Keyboard.isKeyDown(keyCode);
        Color fill = pressed ? pressedColor.getValue() : background.getValue();

        NanoUtil.drawRoundedRect(x, y, w, h, radius, fill);

        float textX = x + w / 2f;
        float textY = y + h / 2f;

        if (PostProcessing.bloom.getValue()) {
            font.drawGlowString(keyName, textX, textY, fs,
                    NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue());
        } else {
            font.drawString(keyName, textX, textY, fs,
                    NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, textColor.getValue(), true);
        }
    }
}
