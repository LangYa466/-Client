package cn.liora.ui.clickgui.component;

import cn.liora.ui.clickgui.ClickGUI;
import cn.liora.value.Value;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public abstract class Component<T> {
    public float posX, posY, width, height;
    public Value<T> value;

    protected float panelWidth = (ClickGUI.width - ClickGUI.leftWidth - 30f) / 2f;
    protected float xGap = 3f;

    public Component(Value<T> value) {
        this.value = value;
    }
    public void init() {

    }
    public abstract void draw(float x, float y, int mouseX, int mouseY);
    public void onMouseClick(int mouseX, int mouseY, int button) {

    }
    public void onMouseRelease() {

    }
    public void onKeyTyped(char c, int keyCode) {

    }
}
