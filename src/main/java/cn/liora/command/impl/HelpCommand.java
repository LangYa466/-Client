package cn.liora.command.impl;

import cn.liora.command.Command;

/**
 * @author ChengFeng
 * @since 2024/7/28
 **/
public class HelpCommand extends Command {
    public HelpCommand() {
        super("HelpCommand", new String[]{"h", "help"});
    }

    @Override
    public void execute(String[] args) {

    }
}
