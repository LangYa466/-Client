package cn.liora.value.impl;

import cn.liora.value.Value;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class BoolValue extends Value<Boolean> {
    public BoolValue(String name, boolean defaultValue) {
        super(name, defaultValue);
    }
}
