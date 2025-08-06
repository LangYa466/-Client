package cn.liora.value.impl;

import cn.liora.value.Value;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class StringValue extends Value<String> {
    public StringValue(String name, String defaultValue) {
        super(name, defaultValue);
    }
}
