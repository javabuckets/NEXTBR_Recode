package com.nextlan.br.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nextlan.br.NEXTBR;
import com.nextlan.br.reference.MessageReference;

public class Spectate implements CommandExecutor
{
	private NEXTBR plugin;
	
	public Spectate(NEXTBR plugin) 
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) 
	{
		Player player = (Player)sender;
		
		if (plugin.isGameActive)
		{
			if (player.getWorld().equals(plugin.br.worldBR) && player.getGameMode().equals(GameMode.SPECTATOR))
			{
				sender.sendMessage(MessageReference.spectateStupid);
			}
			else
			{
				if (player.getGameMode().equals(GameMode.SURVIVAL) && player.getWorld().equals(plugin.br.worldBR))
				{
					sender.sendMessage(MessageReference.spectateInvalid);
				}
				else
				{
					player.setGameMode(GameMode.SPECTATOR);
					player.teleport(plugin.br.brSpawn);
				}
			}
		}
		else
		{
			sender.sendMessage(MessageReference.spectateError);
		}
		
		return false;
	}
}