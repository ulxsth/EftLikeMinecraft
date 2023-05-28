package com.ulxsth.efteconomysystem;

import com.ulxsth.efteconomysystem.db.MoneyDao;
import com.ulxsth.efteconomysystem.db.MoneyDto;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.UUID;

public class EventListener implements Listener {
    private static final EftEconomySystem plugin = EftEconomySystem.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        // DAO発行
        MoneyDao dao = null;
        try {
            dao = new MoneyDao();
        } catch(SQLException err) {
            err.printStackTrace();
        }
        if(dao == null) {
            plugin.getLogger().info("データベースのアクセスに失敗しました。");
            return;
        }

        // UUID取得
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        // DB上に存在するか確認
        if(dao.isExist(uniqueId)) {
            return;
        }

        // DTO作成
        MoneyDto dto = new MoneyDto(
                uniqueId,
                0
        );

        // DBに挿入
        try {
            dao.insert(dto);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
