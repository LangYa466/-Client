package cn.liora.module;

import cn.liora.Client;
import cn.liora.util.MinecraftInstance;
import cn.liora.util.animation.advanced.Animation;
import cn.liora.util.animation.advanced.Direction;
import cn.liora.util.animation.advanced.composed.CustomAnimation;
import cn.liora.util.animation.advanced.impl.SmoothStepAnimation;
import cn.liora.value.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class Module extends MinecraftInstance {
    public String name;
    public ModuleCategory category;
    public int key;
    public boolean enabled;
    public boolean locked = false;
    public List<Value<?>> valueList = new ArrayList<>();

    // Animation
    public CustomAnimation horizontalAnim = new CustomAnimation(SmoothStepAnimation.class, 150, -1d, 1d, Direction.BACKWARDS);
    public Animation verticalAnim = new SmoothStepAnimation(150, 1d, Direction.BACKWARDS);

    public Module(String name, ModuleCategory category, int key, boolean enabled) {
        this.name = name;
        this.category = category;
        this.key = key;
        this.enabled = enabled;
        setAnim(enabled);
    }

    public Module(String name, ModuleCategory category, boolean enabled) {
        this.name = name;
        this.category = category;
        this.enabled = enabled;
        this.key = -1;
        setAnim(enabled);
    }

    public Module(String name, ModuleCategory category, int key) {
        this.name = name;
        this.category = category;
        this.key = key;
        this.enabled = false;
    }

    public Module(String name, ModuleCategory category) {
        this.name = name;
        this.category = category;
        this.enabled = false;
        this.key = -1;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    private void setAnim(boolean state) {
        if (state) {
            if (verticalAnim.getDirection() == Direction.BACKWARDS) {
                verticalAnim.changeDirection();
            }
        } else {
            if (horizontalAnim.getDirection() == Direction.FORWARDS) {
                horizontalAnim.changeDirection();
            }
        }
    }

    public void toggle() {
        if (locked) {
            if (enabled) onDisable(); else onEnable();
            return;
        }

        this.enabled = !this.enabled;
        setAnim(enabled);
        if (enabled) {
            onEnable();
            Client.instance.eventBus.register(this);
        } else {
            onDisable();
            Client.instance.eventBus.unregister(this);
        }
    }
}
