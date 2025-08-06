package cn.liora.module.impl.render;

import cn.liora.module.Module;
import cn.liora.module.ModuleCategory;

/**
 * @author ChengFeng
 * @since 2024/8/5
 **/
public class FullBright extends Module {
    public FullBright() {
        super("FullBright", ModuleCategory.Render);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 1000f;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 0f;
    }
}
