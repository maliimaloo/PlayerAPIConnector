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
    private static final String USERNAME_NULL_OR_EMPTY = "Le nom d'utilisateur ne peut pas être nul ou vide";
    private static final String API_USERNAME_NULL_OR_EMPTY = "Le nom d'utilisateur API ne peut pas être nul ou vide";
    private static final String API_PASSWORD_NULL_OR_EMPTY = "Le mot de passe API ne peut pas être nul ou vide";
    private static final String PLAYER_INFO_FETCH_ERROR = "Erreur lors de la récupération des informations du joueur : ";

    private final HttpClient client;

    public ApiService() {
        this.client = HttpClient.newHttpClient();
    }

    public CompletableFuture<String> getPlayerInfo(String username) {
        assert username != null && !username.trim().isEmpty() : USERNAME_NULL_OR_EMPTY;

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
                    throw new FoException(ex, PLAYER_INFO_FETCH_ERROR + ex.getMessage());
                });
    }

    private String createAuthHeader() {
        String username = ConfigLoader.getInstance().getApiUsername();
        String password = ConfigLoader.getInstance().getApiPassword();
        assert username != null && !username.trim().isEmpty() : API_USERNAME_NULL_OR_EMPTY;
        assert password != null && !password.trim().isEmpty() : API_PASSWORD_NULL_OR_EMPTY;

        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }
}