package com.ulxsth.efteconomysystem;

import org.bukkit.plugin.java.JavaPlugin;

public final class EftEconomySystem extends JavaPlugin {
    private static EftEconomySystem plugin = null;

    private EftEconomySystem() {}

    public static EftEconomySystem getInstance() {
        if(plugin == null) throw new IllegalStateException("メインクラスが初期化されていません");
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
