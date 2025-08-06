package cn.liora.event.impl;

import cn.liora.event.api.CancellableEvent;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class ChatEvent extends CancellableEvent {
    public String text;

    public ChatEvent(String text) {
        this.text = text;
    }
}
