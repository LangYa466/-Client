package cn.feng.untitled.ui.clickgui;

import cn.feng.untitled.module.ModuleCategory;

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
