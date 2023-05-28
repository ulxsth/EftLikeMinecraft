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
import java.util.UUID;

public class PayCommandExecutor implements CommandExecutor {
    public static final String USAGE = "つかいかた: /pay <player> <amount>";
    private static final EftEconomySystem plugin = EftEconomySystem.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player sendPlayer)) {
            plugin.getLogger().info("コンソールから実行できません");
            return true;
        }

        if(args.length != 2) {
            sender.sendMessage(USAGE);
            return true;
        }

        Player receiver = plugin.getServer().getPlayer(args[0]);
        if(receiver == null) {
            sender.sendMessage("プレイヤー名を間違ってるか、プレイヤーがオフラインだよ");
            return true;
        }

        int sendAmount;
        try {
            sendAmount = Integer.parseInt(args[1]);
        } catch(NumberFormatException err) {
            sender.sendMessage("<amount>は整数である必要があります");
            return true;
        }

        try {
            MoneyDao dao = new MoneyDao();
            UUID sendPlayerUniqueId = sendPlayer.getUniqueId();
            UUID receiverUniqueId = receiver.getUniqueId();

            MoneyDto sendPlayerDto = dao.selectByUniqueId(sendPlayerUniqueId);
            MoneyDto receiverDto = dao.selectByUniqueId(receiverUniqueId);

            if(sendPlayerDto.amount() < sendAmount) {
                sender.sendMessage("財布にないお金は送れないよ！！！ばか！！！");
                return true;
            }

            dao.update(sendPlayerDto.decAmount(sendAmount));
            dao.update(sendPlayerDto.addAmount(sendAmount));

            String sendPlayerName = sendPlayer.getName();
            String receiverName = receiver.getName();
            sender.sendMessage(receiverName + "に " + sendAmount + MoneyConstants.CURRENCY_UNIT + "おくったよ");
            receiver.sendMessage(sendPlayerName + "から " + sendAmount + MoneyConstants.CURRENCY_UNIT + "もらったよ");

        } catch(SQLException err) {
            err.printStackTrace();
            sender.sendMessage("なんか処理中にエラーが発生しました。管理人をよんでね～");
            return true;
        }

        return true;
    }
}
