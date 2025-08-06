package cn.liora.command;

import cn.liora.command.impl.HelpCommand;
import cn.liora.event.api.EventTarget;
import cn.liora.event.impl.ChatEvent;
import cn.liora.event.type.PacketType;
import cn.liora.util.misc.ChatUtil;
import cn.liora.util.data.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class CommandManager {
    public String prefix = ".";
    public List<Command> commandList;
    public CommandManager() {
        commandList = new ArrayList<>();
    }

    public void registerCommands() {
        register(new HelpCommand());
    }

    private void register(Command command) {
        commandList.add(command);
    }

    @EventTarget
    private void onChat(ChatEvent event) {
        if (event.getPacketType() == PacketType.RECEIVE || !event.text.startsWith(prefix)) return;
        String text = event.text.substring(1);
        String[] args = text.split(" ");
        System.out.println(text);


        for (Command command : commandList) {
            if (StringUtil.arrayContains(command.patterns, args[0], true)) {
                event.cancel();
                command.execute(args);
                return;
            }
        }

        ChatUtil.sendMessage("Unknown command. Use .help");
        event.cancel();
    }
}
