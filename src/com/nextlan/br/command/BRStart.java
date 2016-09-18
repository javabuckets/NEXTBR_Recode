package com.nextlan.br.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nextlan.br.NEXTBR;
import com.nextlan.br.reference.MessageReference;
import com.nextlan.br.utility.ServerUtil;

public class BRStart implements CommandExecutor
{
	private NEXTBR plugin;

	public BRStart(NEXTBR plugin) 
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) 
	{
		Player player = (Player)sender;

		if (player.isOp())
		{
			if (plugin.playerListener.getBRPlayers().size() > 1)
			{
				plugin.br.startBR();
			}
			
			if (ServerUtil.getPlayersOnline().size() == 1)
			{
				plugin.br.startBR();
				ServerUtil.say(MessageReference.brStart1PersonError);
			}
		}
		else
		{
			sender.sendMessage(MessageReference.brVotestartInfo);
		}
		return false;
	}
}