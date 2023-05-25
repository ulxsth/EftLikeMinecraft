package com.ulxsth.efteconomysystem;

import org.bukkit.plugin.java.JavaPlugin;

public final class EftEconomySystem extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
