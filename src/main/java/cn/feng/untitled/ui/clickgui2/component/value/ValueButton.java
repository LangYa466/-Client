package cn.feng.untitled.ui.clickgui2.component.value;

import cn.feng.untitled.ui.clickgui2.Movable;
import cn.feng.untitled.value.Value;

public abstract class ValueButton<T> extends Movable {
    protected final Value<T> value;

    public ValueButton(Value<T> value) {
        this.value = value;
    }
}