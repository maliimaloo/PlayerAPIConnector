package com.example;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

public class Main extends SimplePlugin {
    @Override
    protected void onPluginStart() {
        Common.log("Activation complete!");
    }

    @Override
    protected void onPluginStop() {
        Common.log("Deactivation complete!");
    }

    public static Main getInstance() {
        return (Main) SimplePlugin.getInstance();
    }
}
