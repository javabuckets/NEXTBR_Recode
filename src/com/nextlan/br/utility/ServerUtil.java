package com.nextlan.br.utility;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ServerUtil 
{
	public static Collection<? extends Player> getPlayersOnline()
	{
		return Bukkit.getOnlinePlayers();
	}
	
	public static void say(String message)
	{
		Bukkit.broadcastMessage(message);
	}
	
	public static void countdownFrom(final int seconds, final String message)
	{
		final Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() 
		{
			int i = seconds;
			
			@Override
			public void run() 
			{
				say(message + ChatColor.RED + Integer.toString(i--) + " seconds");
				
				if (i < 1)
				{
					timer.cancel();
				}
			}
		}, 0, TimeUtil.getMilliseconds(0, 1));
	}
}