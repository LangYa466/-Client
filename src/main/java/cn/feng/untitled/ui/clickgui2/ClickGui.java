package cn.feng.untitled.ui.clickgui2;

import cn.feng.untitled.module.ModuleCategory;
import cn.feng.untitled.util.render.nano.NanoUtil;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {
    private final List<CategoryPanel> panels = new ArrayList<>();

    public ClickGui() {
        for (ModuleCategory category : ModuleCategory.values()) {
            panels.add(new CategoryPanel(this, category));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final float panelY = 20f;
        float panelX = 30f;
        final float panelWidth = 110f;
        for (CategoryPanel panel : panels) {
            panel.update(panelX, panelY, panelWidth, 0f /* height is adaptive */, mouseX, mouseY);
            panelX += panelWidth + 10f;
        }

        NanoUtil.beginFrame();
        NanoUtil.drawRect(0, 0, width, height, new Color(0, 0, 0, 100));
        for (CategoryPanel panel : panels) {
            panel.render();
        }
        NanoUtil.endFrame();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (CategoryPanel panel : panels) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}