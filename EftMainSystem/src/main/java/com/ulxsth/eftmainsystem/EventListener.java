package com.ulxsth.eftmainsystem;

import com.ulxsth.eftmainsystem.db.PlayerDao;
import com.ulxsth.eftmainsystem.db.PlayerDto;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class EventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        if()
    }
}
