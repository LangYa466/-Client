package cn.liora.handler.impl;

import cn.liora.event.api.EventTarget;
import cn.liora.event.impl.AttackEvent;
import cn.liora.event.impl.UpdateEvent;
import cn.liora.event.impl.WorldLoadEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

/**
 * @author LangYa466
 * @date 8/6/2025
 */
public class ComboHandler {
    public static int combo;
    public static Entity lastTarget;

    private final Minecraft mc = Minecraft.getMinecraft();

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.hurtTime > 0) combo = 0;
        if (lastTarget != null && lastTarget.getDistanceToEntity(mc.thePlayer) >= 8) combo = 0;
    }

    @EventTarget
    public void onWorldLoad(WorldLoadEvent event) {
        combo = 0;
    }

    @EventTarget
    public void onAttack(AttackEvent event) {
        if (lastTarget != event.getTarget()) {
            combo = 0;
        } else {
            combo++;
        }
        lastTarget = event.getTarget();
    }
}
