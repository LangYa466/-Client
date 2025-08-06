package cn.liora.module.impl.client;

import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;
import cn.liora.value.impl.BoolValue;
import cn.liora.value.impl.NumberValue;

/**
 * @author ChengFeng
 * @since 2024/8/8
 **/
public class EntityCullingMod extends Module {
    public EntityCullingMod() {
        super("EntityCulling", ModuleCategory.Client);
    }

    public static final BoolValue through = new BoolValue("NameTagThroughWalls", true);
    public static final BoolValue armorStands = new BoolValue("SkipArmorStands", true);
    public static final NumberValue tracingDist = new NumberValue("TracingDistance", 128f, 256f, 64f, 1f);
    public static final NumberValue sleepDelay = new NumberValue("SleepDelay", 10f, 20f, 5f, 1f);
    public static final NumberValue hitboxLimit = new NumberValue("HitboxLimit", 50f, 80f, 30f, 1f);
}
