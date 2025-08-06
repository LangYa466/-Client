package cn.liora.module.impl.render;

import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import cn.liora.value.impl.BoolValue;

/**
 * This module only have one instance, so all fields are static.
 * @author ChengFeng
 * @since 2024/7/29
 **/
public class GUI extends Module {
    public GUI() {
        super("GUI", ModuleCategory.Render);
        locked = true;
    }

    public static final BoolValue renderBossHealth = new BoolValue("RenderBossHealth", true);
    public static final BoolValue renderPumpkin = new BoolValue("RenderPumpkin", true);
    public static final BoolValue renderChatRect = new BoolValue("RenderChatRect", false);
    public static final BoolValue chatFancyFont = new BoolValue("ChatFancyFont", true);
    public static final BoolValue infinityChat = new BoolValue("InfinityChat", true);
}
