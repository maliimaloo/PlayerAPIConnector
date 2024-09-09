package com.example.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigLoader {
    private static ConfigLoader instance;

    private final Dotenv dotenv;

    public ConfigLoader() {
        this.dotenv = Dotenv.load();
    }

    public String getApiUsername() {
        return dotenv.get("API_USERNAME");
    }

    public String getApiPassword() {
        return dotenv.get("API_PASSWORD");
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }

        return instance;
    }
}
