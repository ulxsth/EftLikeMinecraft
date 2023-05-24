package com.ulxsth.eftmainsystem;

import org.bukkit.plugin.java.JavaPlugin;

public final class EftMainSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
