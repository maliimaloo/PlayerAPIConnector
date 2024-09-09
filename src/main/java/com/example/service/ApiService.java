package com.example.service;

import com.example.config.ConfigLoader;
import org.mineacademy.fo.exception.FoException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class ApiService {
    private final HttpClient client;

    public ApiService() {
        this.client = HttpClient.newHttpClient();
    }

    public CompletableFuture<String> getPlayerInfo(String username) {
        assert username != null && !username.trim().isEmpty() : "Le nom d'utilisateur ne peut pas être nul ou vide";

        String apiUrl = "http://localhost:8080/players/getOrCreate/" + username;
        String authHeader = createAuthHeader();

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

    private String createAuthHeader() {
        String username = ConfigLoader.getInstance().getApiUsername();
        String password = ConfigLoader.getInstance().getApiPassword();
        assert username != null && !username.trim().isEmpty() : "Le nom d'utilisateur API ne peut pas être nul ou vide";
        assert password != null && !password.trim().isEmpty() : "Le mot de passe API ne peut pas être nul ou vide";

        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }
}