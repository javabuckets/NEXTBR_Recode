package com.nextlan.br.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nextlan.br.NEXTBR;
import com.nextlan.br.reference.MessageReference;

public class Lobby implements CommandExecutor
{
	private NEXTBR plugin;
	
	public Lobby(NEXTBR plugin) 
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) 
	{
		Player player = (Player)sender;
		
		if (!player.getGameMode().equals(GameMode.SURVIVAL))
		{
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(plugin.br.lobbySpawn);
		}
		else if (player.getWorld().equals(plugin.br.worldBR) && player.getGameMode().equals(GameMode.SURVIVAL))
		{
			sender.sendMessage(MessageReference.lobbyJoinError);
		}
		
		return false;
	}
}