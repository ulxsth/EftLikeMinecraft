package com.ulxsth.efteconomysystem;

import com.ulxsth.efteconomysystem.commands.PayCommandExecutor;
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
        // シングルトン初期化
        plugin = this;

        // イベントリスナ登録
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        // コマンド登録
        getCommand("stats").setExecutor(new StatsCommandExecutor());
        getCommand("pay").setExecutor(new PayCommandExecutor());
    }
}
