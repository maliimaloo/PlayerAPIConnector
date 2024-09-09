package com.example;

import com.example.service.ApiService;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

public class Main extends SimplePlugin {
    private ApiService apiService;

    public ApiService getApiService() {
        return apiService;
    }

    @Override
    protected void onPluginStart() {
        Common.log("Activation complete!");

        this.apiService = new ApiService();
    }

    @Override
    protected void onPluginStop() {
        Common.log("Deactivation complete!");
    }

    public static Main getInstance() {
        return (Main) SimplePlugin.getInstance();
    }
}
