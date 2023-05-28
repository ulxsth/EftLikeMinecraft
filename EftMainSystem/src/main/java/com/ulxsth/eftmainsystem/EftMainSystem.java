package com.ulxsth.eftmainsystem;

import com.ulxsth.eftmainsystem.commands.PayCommandExecutor;
import com.ulxsth.eftmainsystem.commands.RecordCommandExecutor;
import com.ulxsth.eftmainsystem.commands.StatsCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class EftMainSystem extends JavaPlugin {
    private static EftMainSystem instance = null;

    public static EftMainSystem getInstance() {
        if(instance == null) throw new IllegalStateException("メインクラスが初期化されていません");
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.getServer().getPluginManager().registerEvents(new EventListener(), instance);
        getCommand("record").setExecutor(new RecordCommandExecutor());
        getCommand("stats").setExecutor(new StatsCommandExecutor());
        getCommand("pay").setExecutor(new PayCommandExecutor());
    }
}
