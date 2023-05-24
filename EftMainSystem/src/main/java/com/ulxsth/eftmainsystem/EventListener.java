package com.ulxsth.eftmainsystem;

import com.ulxsth.eftmainsystem.db.PlayerDao;
import com.ulxsth.eftmainsystem.db.PlayerDto;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class EventListener implements Listener {
    private static final EftMainSystem plugin = EftMainSystem.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        // DAO発行
        PlayerDao dao = null;
        try {
            dao = new PlayerDao();
        } catch (SQLException err) {
            err.printStackTrace();
        }
        if(dao == null) {
            plugin.getLogger().info("データベースへのアクセスに失敗しました。");
            return;
        }

        // UniqueID取得
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        // 既存プレイヤーか確認
        if(dao.isExist(uniqueId)) return;

        // DTO作成
        PlayerDto dto = new PlayerDto(
                uniqueId,
                player.getName(),
                new Date(),
                new Date()
        );

        // 挿入
        try {
            dao.insert(dto);
        } catch(SQLException err) {
            err.printStackTrace();
        }

        // ログ出力
        plugin.getLogger().info("プレイヤー情報を登録しました。");
        plugin.getLogger().info("プレイヤー名: " + player.getName());
    }
}
