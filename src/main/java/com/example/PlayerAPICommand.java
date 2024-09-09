package com.example;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class PlayerCommand extends SimpleCommand {
    public PlayerCommand() {
        super("playerapi");
    }

    @Override
    protected void onCommand() {

    }
}
