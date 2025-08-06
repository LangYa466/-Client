package cn.liora;

import cn.liora.command.CommandManager;
import cn.liora.config.ConfigManager;
import cn.liora.event.EventBus;
import cn.liora.handler.HandlerManager;
import cn.liora.module.ModuleManager;
import cn.liora.ui.UIManager;
import cn.liora.ui.font.awt.AWTFontLoader;
import cn.liora.util.misc.Logger;
import de.florianmichael.viamcp.ViaMCP;
import dev.tr7zw.entityculling.EntityCulling;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public enum Client {
    instance;

    public final String CLIENT_NAME = "Liora";
    public EventBus eventBus;
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public UIManager uiManager;
    public ConfigManager configManager;
    public HandlerManager handlerManager;

    public boolean loaded = false;

    public void start() {
        Logger.info("Client starting up...");
        long start = System.currentTimeMillis();

        try {
            ViaMCP.create();
            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        EntityCulling.instance.onInitialize();

        Logger.info("Initializing managers...");
        eventBus = new EventBus();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        uiManager = new UIManager();
        configManager = new ConfigManager();
        handlerManager = new HandlerManager();

        Logger.info("Loading fonts...");
        AWTFontLoader.registerFonts();

        Logger.info("Registering...");
        eventBus.register(moduleManager);
        eventBus.register(commandManager);
        eventBus.register(uiManager);
        moduleManager.registerModules();
        commandManager.registerCommands();
        configManager.registerConfigs();
        uiManager.registerWidgets();

        Logger.info("Miscellaneous...");
        configManager.loadConfigs();
        uiManager.initGUI();
        Keyboard.enableRepeatEvents(false);

        Display.setTitle(CLIENT_NAME + " | LWJGL Version " + Sys.getVersion());
        Logger.info("Finished loading in " + (System.currentTimeMillis() - start) / 1000f + " seconds.");

        loaded = true;
    }

    public void stop() {
        Logger.info("Client stopping...");
        Logger.info("Saving configs...");
        configManager.saveConfigs();
    }
}
