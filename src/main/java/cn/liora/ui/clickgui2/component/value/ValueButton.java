package cn.liora.ui.clickgui2.component.value;

import cn.liora.ui.clickgui2.Movable;
import cn.liora.value.Value;

public abstract class ValueButton<T> extends Movable {
    protected final Value<T> value;

    public ValueButton(Value<T> value) {
        this.value = value;
    }
}