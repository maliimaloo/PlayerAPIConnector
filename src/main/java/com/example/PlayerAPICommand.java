package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.exception.FoException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@AutoRegister
public final class PlayerAPICommand extends SimpleCommand {
    private final HttpClient client;

    // Constructeur de la commande
    public PlayerAPICommand() {
        super("playerapi");

        this.client = HttpClient.newHttpClient();
    }

    @Override
    protected void onCommand() {
        checkArgs(1, "Vous devez spécifier un nom d'utilisateur.");

        String username = args[0];
        this.getPlayerInfoFromAPI(username)
                .thenAccept((response) -> tell("Player Info: " + response))
                .exceptionally(ex -> {
                    tellError("Une erreur s'est produite lors de la récupération des informations du joueur: " + ex.getMessage());
                    return null;
                });
    }

    private CompletableFuture<String> getPlayerInfoFromAPI(String username) {
        String apiUrl = "http://localhost:8080/players/getOrCreate/" + username;

        Dotenv dotenv = Dotenv.load();
        String auth = dotenv.get("API_USERNAME") + ":" + dotenv.get("API_PASSWORD");
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", authHeader)
                .GET()
                .build();

        return this.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(ex -> {
                    throw new FoException(ex, "Erreur lors de la récupération des informations du joueur : " + ex.getMessage());
                });
    }
}