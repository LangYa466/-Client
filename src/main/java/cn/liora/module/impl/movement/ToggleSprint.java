package cn.liora.module.impl.movement;

import cn.liora.event.api.EventTarget;
import cn.liora.event.impl.MotionEvent;
import cn.liora.event.type.EventType;
import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class ToggleSprint extends Module {
    public ToggleSprint() {
        super("ToggleSprint", ModuleCategory.Movement, true);
    }

    @EventTarget
    private void onMotion(MotionEvent event) {
        mc.gameSettings.keyBindSprint.pressed = true;
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null) return;
        mc.thePlayer.setSprinting(false);
    }
}
