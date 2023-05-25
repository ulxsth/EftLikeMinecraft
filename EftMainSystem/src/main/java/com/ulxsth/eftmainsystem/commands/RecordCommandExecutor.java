package com.ulxsth.eftmainsystem.commands;

import com.ulxsth.eftmainsystem.db.PlayerDao;
import com.ulxsth.eftmainsystem.db.PlayerDto;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class RecordCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // コンソールチェック
        if(!(sender instanceof Player)) {
            sender.sendMessage("コンソールから実行することはできません");
        }

        // UUID取得
        Player player = (Player) sender;
        UUID uniqueId = player.getUniqueId();

        // データ取得
        PlayerDto result = null;
        try {
            PlayerDao dao = new PlayerDao();
            result = dao.selectByUniqueId(uniqueId);
        } catch (SQLException err) {
            err.printStackTrace();
        }
        if(result == null) {
            throw new NullPointerException("データの取得中に不具合が発生しました");
        }
        String name = result.name();
        Date firstLoggedIn = result.firstLoggedIn();

        // 表示
        sender.sendMessage("§f[§a " + name + " §f]");
        sender.sendMessage("§f| 最初にログインした日: " + firstLoggedIn.toString());

        return true;
    }
}
