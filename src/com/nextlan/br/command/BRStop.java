package com.nextlan.br.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nextlan.br.NEXTBR;
import com.nextlan.br.reference.MessageReference;

public class BRStop implements CommandExecutor
{
	private NEXTBR plugin;

	public BRStop(NEXTBR plugin) 
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) 
	{
		Player player = (Player)sender;

		if (player.isOp())
		{
			if (plugin.isGameActive)
			{
				plugin.br.endBR();
			}
			else
			{
				sender.sendMessage(MessageReference.noOngoingBR);
			}
		}

		return false;
	}
}