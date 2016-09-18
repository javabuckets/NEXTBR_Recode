package com.nextlan.br.command;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.nextlan.br.NEXTBR;
import com.nextlan.br.reference.MessageReference;
import com.nextlan.br.utility.CommandUtil;

public class AutoFollow implements CommandExecutor, TabCompleter
{
	private NEXTBR plugin;

	public HashMap<Player, Player> autoFollowMap = new HashMap<Player, Player>();

	public AutoFollow(NEXTBR plugin) 
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) 
	{
		if (args.length == 0)
		{
			sender.sendMessage(MessageReference.autofollowInfo);
			return false;
		}

		Player player = (Player)sender;
		Player targetPlayer = player.getServer().getPlayer(args[0]);

		try
		{
			if (!targetPlayer.equals(player))
			{
				if (!autoFollowMap.containsKey(player))
				{
					autoFollowMap.put(player, targetPlayer);
					sender.sendMessage(MessageReference.autofollowNowFollowing(targetPlayer));
				}
				else
				{
					if (autoFollowMap.get(player) != targetPlayer)
					{
						sender.sendMessage(MessageReference.autofollowNowFollowing2(targetPlayer, autoFollowMap.get(player)));
						autoFollowMap.replace(player, targetPlayer);
					}
					else
					{
						sender.sendMessage(MessageReference.autofollowAlreadyFollowing);
					}
				}
			}
			else
			{
				sender.sendMessage(MessageReference.autofollowFollowYourself);
			}
		} catch (NullPointerException e)
		{
			sender.sendMessage(MessageReference.autofollowErrorMsg);
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String str, String[] args) 
	{
		if (args.length > 0)
		{
			return CommandUtil.getTabCompletePlayers();
		}
		return null;
	}
}