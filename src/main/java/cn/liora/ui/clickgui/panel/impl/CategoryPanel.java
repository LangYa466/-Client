package cn.liora.ui.clickgui.panel.impl;

import cn.liora.Client;
import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import cn.liora.ui.clickgui.panel.Panel;
import cn.liora.ui.font.awt.AWTFontLoader;
import cn.liora.util.animation.simple.SimpleAnimation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengFeng
 * @since 2024/7/31
 **/
public class CategoryPanel extends Panel {
    public ModuleCategory category;
    public List<ModulePanel> modulePanelList;
    public SimpleAnimation scrollAnim;


    public CategoryPanel(ModuleCategory category) {
        this.category = category;
        width = 80f;
        height = 30f;
        modulePanelList = new ArrayList<>();

        for (Module module : Client.instance.moduleManager.getModuleByCategory(category)) {
            modulePanelList.add(new ModulePanel(module));
        }

        scrollAnim = new SimpleAnimation(1);
    }

    @Override
    public void draw(float x, float y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        this.height = AWTFontLoader.greyCliff(18).getFontHeight();
        scrollAnim.speed = 0.2f;

        AWTFontLoader.greyCliff(18).drawString(category.name(), x, y, Color.WHITE.getRGB());
    }

    public void handleScroll() {
        // Scroll
        int wheel = Mouse.getDWheel();
        if (wheel != 0) {
            if (wheel > 0) {
                scrollAnim.target += 20;
            } else {
                scrollAnim.target -= 20;
            }
            if (scrollAnim.target > 0) scrollAnim.target = 0f;
        }
    }
}
