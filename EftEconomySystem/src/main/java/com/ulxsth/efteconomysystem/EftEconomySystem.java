package com.ulxsth.efteconomysystem;

import com.ulxsth.efteconomysystem.commands.StatsCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class EftEconomySystem extends JavaPlugin {
    private static EftEconomySystem plugin = null;

    public static EftEconomySystem getInstance() {
        if(plugin == null) throw new IllegalStateException("メインクラスが初期化されていません");
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        getCommand("stats").setExecutor(new StatsCommandExecutor());
    }
}
