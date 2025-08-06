package cn.liora.event.impl;

import cn.liora.event.api.Event;

/**
 * @author ChengFeng
 * @since 2024/8/5
 **/
public class Render3DEvent extends Event {
    public float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
