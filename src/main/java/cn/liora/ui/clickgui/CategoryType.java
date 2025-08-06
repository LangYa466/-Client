package cn.liora.ui.clickgui;

import cn.liora.module.ModuleCategory;

public enum CategoryType {
    Visual,
    Misc,
    Setting;

    public static CategoryType getType(ModuleCategory category) {
        switch (category) {
            case Client -> {
                return Setting;
            }

            case Render, Widget -> {
                return Visual;
            }

            default -> {
                return Misc;
            }
         }
    }
}
