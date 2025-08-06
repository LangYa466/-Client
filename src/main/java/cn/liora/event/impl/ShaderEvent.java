package cn.liora.event.impl;

import cn.liora.event.api.Event;

/**
 * @author ChengFeng
 * @since 2024/8/3
 **/
public class ShaderEvent extends Event {
    public boolean bloom;

    public ShaderEvent(boolean bloom) {
        this.bloom = bloom;
    }
}
