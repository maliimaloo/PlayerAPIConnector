package com.example.command;

import com.example.service.ApiService;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class PlayerAPICommand extends SimpleCommand {
    private final ApiService apiService;

    public PlayerAPICommand() {
        super("playerapi");

        this.apiService = new ApiService();
    }

    @Override
    protected void onCommand() {
        checkArgs(1, "Vous devez spécifier un nom d'utilisateur.");

        String username = args[0];
        this.apiService.getPlayerInfo(username)
                .thenAccept(response -> tell("Player Info: " + response))
                .exceptionally(ex -> {
                    tellError("Une erreur s'est produite lors de la récupération des informations du joueur: " + ex.getMessage());
                    return null;
                });
    }
}