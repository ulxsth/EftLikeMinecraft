package com.ulxsth.eftmainsystem.commands;

import com.ulxsth.eftmainsystem.EftMainSystem;
import com.ulxsth.eftmainsystem.constants.MessageConstants;
import com.ulxsth.eftmainsystem.constants.MoneyConstants;
import com.ulxsth.eftmainsystem.db.dao.MoneyDao;
import com.ulxsth.eftmainsystem.db.dto.MoneyDto;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class PayCommandExecutor implements CommandExecutor {
    public static final String USAGE = MessageConstants.INFO + "つかいかた: /pay <player> <amount>";
    private static final EftMainSystem plugin = EftMainSystem.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // コンソールは弾く
        if(!(sender instanceof Player sendPlayer)) {
            plugin.getLogger().info("コンソールから実行できません");
            return true;
        }

        // 引数が足りない場合は弾く
        if(args.length != 2) {
            sender.sendMessage(USAGE);
            return true;
        }

        // 受け取り手がいない場合は弾く
        Player receiver = plugin.getServer().getPlayer(args[0]);
        if(receiver == null) {
            sender.sendMessage(MessageConstants.WARN + "プレイヤー名を間違ってるか、プレイヤーがオフラインだよ");
            return true;
        }

        // amountが数値でない場合は弾く
        int sendAmount;
        try {
            sendAmount = Integer.parseInt(args[1]);
        } catch(NumberFormatException err) {
            sender.sendMessage(MessageConstants.WARN + "<amount>は整数だよ～");
            return true;
        }

        try {
            // DAO発行
            MoneyDao dao = new MoneyDao();

            // UUID取得
            UUID sendPlayerUniqueId = sendPlayer.getUniqueId();
            UUID receiverUniqueId = receiver.getUniqueId();

            // DTO作成
            MoneyDto sendPlayerDto = dao.selectByUniqueId(sendPlayerUniqueId);
            MoneyDto receiverDto = dao.selectByUniqueId(receiverUniqueId);

            // 送り手に十分な所持金がない場合ははじく
            if(sendPlayerDto.amount() < sendAmount) {
                sender.sendMessage(MessageConstants.WARN + "財布にないお金は送れないよ！！！ばか！！！");
                return true;
            }

            // 送金処理
            dao.update(sendPlayerDto.decAmount(sendAmount));
            dao.update(sendPlayerDto.addAmount(sendAmount));

            // 結果表示
            String sendPlayerName = sendPlayer.getName();
            String receiverName = receiver.getName();
            sender.sendMessage(MessageConstants.INFO + receiverName + "に " + sendAmount + MoneyConstants.CURRENCY_UNIT + "おくったよ");
            receiver.sendMessage(MessageConstants.INFO + sendPlayerName + "から " + sendAmount + MoneyConstants.CURRENCY_UNIT + "もらったよ");

        } catch(SQLException err) {
            err.printStackTrace();
            sender.sendMessage(MessageConstants.ERROR + "なんか処理中にエラーが発生しました。管理人をよんでね～");
            return true;
        }

        return true;
    }
}
