package cn.feng.untitled.ui.clickgui2.component;

import cn.feng.untitled.module.Module;
import cn.feng.untitled.ui.clickgui2.CategoryPanel;
import cn.feng.untitled.ui.clickgui2.Movable;
import cn.feng.untitled.ui.clickgui2.component.value.*;
import cn.feng.untitled.ui.font.nano.NanoFontLoader;
import cn.feng.untitled.util.animation.advanced.composed.ColorAnimation;
import cn.feng.untitled.util.render.nano.NanoUtil;
import cn.feng.untitled.value.Value;
import cn.feng.untitled.value.impl.*;
import lombok.Getter;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton extends Movable {
    private final CategoryPanel parent;
    private final Module module;
    private final List<ValueButton<?>> valueButtons = new ArrayList<>();
    // 添加缺失的方法
    @Getter
    private boolean expanded = false;
    private final ColorAnimation bgColor = new ColorAnimation(new Color(50, 50, 50), new Color(50, 50, 50), 300);
    private final ColorAnimation textColor = new ColorAnimation(Color.WHITE, Color.WHITE, 300);

    public ModuleButton(CategoryPanel parent, Module module) {
        this.parent = parent;
        this.module = module;

        if (!module.valueList.isEmpty()) {
            for (Value<?> value : module.valueList) {
                if (value instanceof BoolValue) {
                    valueButtons.add(new BoolValueButton((BoolValue) value));
                } else if (value instanceof ColorValue) {
                    valueButtons.add(new ColorValueButton((ColorValue) value));
                } else if (value instanceof ModeValue) {
                    valueButtons.add(new ModeValueButton((ModeValue) value));
                } else if (value instanceof NumberValue) {
                    valueButtons.add(new NumberValueButton((NumberValue) value));
                } else if (value instanceof StringValue) {
                    valueButtons.add(new StringValueButton((StringValue) value));
                }
            }
        }
    }

    public float getExpandedHeight() {
        if (!expanded) return 0;
        float height = 0f;
        for (ValueButton<?> button : valueButtons) {
            height += 15f; // 每个 Value 组件的高度
        }
        return height;
    }

    @Override
    public void render() {
        NanoUtil.drawRect(x, y, width, height, bgColor.getOutput());
        NanoFontLoader.misans.bold().drawString(module.name, x + 10f, y + height / 2f, 14f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, textColor.getOutput());

        if (expanded) {
            float valueY = y + height;
            for (ValueButton<?> button : valueButtons) {
                button.x = this.x;
                button.y = valueY;
                button.width = this.width;
                button.height = 15f;
                button.render();
                valueY += button.height;
            }
        }
    }

    @Override
    public void update(float x, float y, float width, float height, int mouseX, int mouseY) {
        super.update(x, y, width, height, mouseX, mouseY);
        if (hovering) {
            bgColor.change(module.enabled ? new Color(15, 49, 97).brighter() : new Color(70, 70, 70));
        } else {
            bgColor.change(module.enabled ? new Color(15, 49, 97) : new Color(50, 50, 50));
        }

        textColor.change(module.enabled ? Color.BLACK : Color.WHITE);

        if (expanded) {
            float valueY = y + height;
            for (ValueButton<?> button : valueButtons) {
                button.update(x, valueY, width, 15f, mouseX, mouseY);
                valueY += 15f;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovering) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 1) {
                if (!valueButtons.isEmpty()) {
                    expanded = !expanded;
                    if (expanded) {
                        parent.applyExpand(getExpandedHeight());
                    } else {
                        parent.applyFold(getExpandedHeight());
                    }
                }
            }
        }

        if (expanded) {
            for (ValueButton<?> button : valueButtons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
}