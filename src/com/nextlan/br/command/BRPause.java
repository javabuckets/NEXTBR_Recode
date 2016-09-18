package com.nextlan.br.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.nextlan.br.NEXTBR;

public class BRPause implements CommandExecutor
{
	private NEXTBR plugin;
	
	public BRPause(NEXTBR plugin) 
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) 
	{
		
		return false;
	}
}