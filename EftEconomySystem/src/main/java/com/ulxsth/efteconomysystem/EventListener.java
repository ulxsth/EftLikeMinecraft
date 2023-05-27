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

        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        if(dao.isExist(uniqueId)) {
            return;
        }

        MoneyDto dto = new MoneyDto(
                uniqueId,
                0
        );

        try {
            dao.insert(dto);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
