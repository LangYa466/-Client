package cn.liora.ui.clickgui2;

import cn.liora.Client;
import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import cn.liora.ui.clickgui2.component.ModuleButton;
import cn.liora.ui.font.nano.NanoFontLoader;
import cn.liora.util.animation.advanced.composed.CustomAnimation;
import cn.liora.util.animation.advanced.impl.EaseOutCubic;
import cn.liora.util.data.StringUtil;
import cn.liora.util.render.RenderUtil;
import cn.liora.util.render.nano.NanoUtil;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel extends Movable {
    private final ClickGui parent;
    private final List<ModuleButton> buttons = new ArrayList<>();
    private final ModuleCategory category;

    private final float maxHeight = 250f;
    private float minHeight;
    private final CustomAnimation heightAnim = new CustomAnimation(EaseOutCubic.class, 300, 0d, 0d);
    private float totalHeight;

    public CategoryPanel(ClickGui parent, ModuleCategory category) {
        this.parent = parent;
        this.category = category;

        for (Module module : Client.instance.moduleManager.getModuleByCategory(category)) {
            buttons.add(new ModuleButton(this, module));
        }
        recalculateHeight();
        heightAnim.setEndPoint(minHeight);
    }

    protected final CustomAnimation scrollAnimation = new CustomAnimation(EaseOutCubic.class, 120, 0d, 0d);
    protected float maxScroll;

    public void handleScroll() {
        int wheel = Mouse.getDWheel();
        if (wheel > 0) {
            scrollAnimation.setEndPoint(scrollAnimation.getEndPoint() + 30f);
        } else if (wheel < 0) {
            scrollAnimation.setEndPoint(scrollAnimation.getEndPoint() - 30f);
        }

        if (scrollAnimation.getEndPoint() > 0f) {
            scrollAnimation.setEndPoint(0f);
        }
        if (scrollAnimation.getEndPoint() < -maxScroll) {
            scrollAnimation.setEndPoint(-maxScroll);
        }
    }

    private void recalculateHeight() {
        float calculatedHeight = 17f + 3f; // Header and padding
        for (ModuleButton button : buttons) {
            calculatedHeight += 20f; // Base height for each button
        }
        this.totalHeight = calculatedHeight;
        this.minHeight = Math.min(maxHeight, totalHeight);
    }

    @Override
    public void update(float x, float y, float width, float height, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = heightAnim.getOutput().floatValue();
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.hovering = RenderUtil.hovering(mouseX, mouseY, x, y, width, this.height);
        this.maxScroll = Math.max(totalHeight - this.height, 0);

        float buttonY = y + scrollAnimation.getOutput().floatValue() + 17f;
        final float buttonHeight = 20f;
        for (ModuleButton button : buttons) {
            button.update(x + 1f, buttonY, width - 2f, buttonHeight, mouseX, mouseY);
            buttonY += buttonHeight + button.getExpandedHeight();
        }

        if (this.hovering) handleScroll();
    }

    public void applyExpand(float height) {
        this.totalHeight += height;
        heightAnim.setEndPoint(Math.min(maxHeight, heightAnim.getEndPoint() + height));
    }

    public void applyFold(float height) {
        this.totalHeight -= height;
        heightAnim.setEndPoint(Math.max(minHeight, heightAnim.getEndPoint() - height));
    }

    public void render() {
        NanoUtil.drawRoundedRect(x, y, width, height, 5f, new Color(30, 30, 30));
        NanoFontLoader.misans.bold().drawString(StringUtil.capitalizeFirstLetter(category.name()), x + 10f, y + 4f, 16f, Color.WHITE);
        NanoUtil.scissorStart(x, y + 17f, width, height - 17f);
        for (ModuleButton button : buttons) {
            button.render();
        }
        NanoUtil.scissorEnd();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovering) {
            for (ModuleButton button : buttons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
}