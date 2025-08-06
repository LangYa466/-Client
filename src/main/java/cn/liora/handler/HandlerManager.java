package cn.liora.handler;


import cn.liora.Client;
import cn.liora.handler.impl.ComboHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LangYa466
 * @date 8/6/2025
 */
public class HandlerManager {
    public List<Object> handlerList;

    public HandlerManager() {
        this.handlerList = new ArrayList<>();
        registerHandler();
    }

    public void registerHandler() {
        register(new ComboHandler());
    }

    public void register(Object object) {
        handlerList.add(object);
        Client.instance.eventBus.register(object);
    }
}
