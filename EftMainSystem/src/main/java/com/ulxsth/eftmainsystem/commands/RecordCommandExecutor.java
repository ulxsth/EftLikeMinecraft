package com.ulxsth.eftmainsystem.commands;

import com.ulxsth.eftmainsystem.EftMainSystem;
import com.ulxsth.eftmainsystem.constants.MessageConstants;
import com.ulxsth.eftmainsystem.db.dao.PlayerDao;
import com.ulxsth.eftmainsystem.db.dto.PlayerDto;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class RecordCommandExecutor implements CommandExecutor {
    private static final EftMainSystem plugin = EftMainSystem.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // コンソールチェック
        if(!(sender instanceof Player)) {
            plugin.getLogger().info("コンソールから実行することはできません");
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
            sender.sendMessage(MessageConstants.ERROR + "なんか処理中にエラーが発生しました。管理人をよんでね～");
            err.printStackTrace();
            return true;
        }
        if(result == null) {
            sender.sendMessage(MessageConstants.ERROR + "なんか処理中にエラーが発生しました。管理人をよんでね～");
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
