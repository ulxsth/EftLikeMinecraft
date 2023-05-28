package com.ulxsth.efteconomysystem.commands;

import com.ulxsth.efteconomysystem.EftEconomySystem;
import com.ulxsth.efteconomysystem.constants.MoneyConstants;
import com.ulxsth.efteconomysystem.db.MoneyDao;
import com.ulxsth.efteconomysystem.db.MoneyDto;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class StatsCommandExecutor implements CommandExecutor {
    private static final EftEconomySystem plugin = EftEconomySystem.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // コンソールははじく
        if(args.length == 0 && !(sender instanceof Player)) {
            plugin.getLogger().info("コンソールからは実行できません");
        }

        // UUID取得
        Player player = (Player) sender;
        java.util.UUID uniqueId = player.getUniqueId();

        // データ取得
        MoneyDto result = null;
        try {
            MoneyDao dao = new MoneyDao();
            result = dao.selectByUniqueId(uniqueId);
        } catch (SQLException err) {
            err.printStackTrace();
        }
        if(result == null) {
            throw new IllegalStateException("データベースに存在しないプレイヤーです");
        }

        // 結果送信
        String name = player.getName();
        int amount = result.amount();
        player.sendMessage("§f[§a " + name + " §f]");
        player.sendMessage("§f| 所持金: " + amount + " " + MoneyConstants.CURRENCY_UNIT);

        return true;
    }
}
