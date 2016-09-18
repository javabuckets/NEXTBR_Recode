package com.nextlan.br;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.nextlan.br.reference.MessageReference;
import com.nextlan.br.utility.CommandUtil;
import com.nextlan.br.utility.ServerUtil;
import com.nextlan.br.utility.TimeUtil;

public class BattleRoyale 
{
	NEXTBR plugin;
	
	public World lobby = Bukkit.getWorld("Lobby");
	public World worldBR = null;
	
	public Location lobbySpawn = lobby.getSpawnLocation();
	public Location brSpawn = null;
	
	// Game Settings
	public int diameter = 250;
	public int endDiameter = 50;
	public int gameTime = TimeUtil.getSeconds(3);
	long gameEndWaitTime = TimeUtil.getMilliseconds(0, 15);
	
	// Game Timers
	public Timer oneMinWarn = new Timer();
	public Timer halfMinWarn = new Timer();
	public Timer tenSecWarn = new Timer();
	
	public BattleRoyale(NEXTBR plugin) 
	{
		this.plugin = plugin;
	}
	
	public void startBR()
	{
		for (Player p : ServerUtil.getPlayersOnline())
		{
			if (p.getGameMode().equals(GameMode.SPECTATOR) && (!p.isOp()))
			{
				p.setGameMode(GameMode.SURVIVAL);
			}
		}
		
		plugin.reloadTimer();
		
		createNewBRWorld();
		
		// Replace with method that finds a suitable spawnpoint
		worldBR.setSpawnLocation(0, worldBR.getHighestBlockYAt(0, 0), 0);
		brSpawn = worldBR.getSpawnLocation();
		
		worldBR.setPVP(false);
		worldBR.setDifficulty(Difficulty.NORMAL);
		
		ServerUtil.say(MessageReference.brStarts);
		
		ServerUtil.countdownFrom(10, MessageReference.brStarting);
		
		plugin.timer.schedule(new TimerTask()
		{
			public void run() 
			{
				ServerUtil.say(MessageReference.brStarted(plugin.playerListener.getBRPlayers()));
				ServerUtil.say(MessageReference.pvpEnabledIn);
				
				startBR(diameter);
				
				plugin.isGameActive = true;
				
				/*
				 * PVP Warnings
				 */
				oneMinWarn = new Timer();
				halfMinWarn = new Timer();
				tenSecWarn = new Timer();
				
				oneMinWarn.schedule(new TimerTask()
				{
					public void run()
					{
						ServerUtil.say(MessageReference.pvpOneMinWarn);
					}
				}, TimeUtil.getMilliseconds(1, 0));
				

				halfMinWarn.schedule(new TimerTask()
				{
					public void run()
					{
						ServerUtil.say(MessageReference.pvpHalfMinWarn);
					}
				}, TimeUtil.getMilliseconds(1, 30));
				

				tenSecWarn.schedule(new TimerTask()
				{
					public void run()
					{
						ServerUtil.countdownFrom(10, MessageReference.pvpTenSecCountdown);
					}
				}, TimeUtil.getMilliseconds(1, 50));
				
				plugin.timer.schedule(new TimerTask()
				{
					public void run()
					{
						worldBR.setPVP(true);
						ServerUtil.say(MessageReference.pvpEnabled);
						
						startWorldBorderShrink(endDiameter, gameTime);
					}
				}, TimeUtil.getMilliseconds(2, 0));
			}
			
			private void startBR(int worldBorderDiameter)
			{
				for (Player p : plugin.playerListener.getBRPlayers())
				{
					setPlayerBRReady(p);
				}
				
				worldBR.setTime(1000);
				
				setWorldBorder(brSpawn, 10.0, 0.1, diameter, 30);
			}
			
			private void setPlayerBRReady(Player player)
			{
				player.setHealth(20);
				player.setFoodLevel(20);
				player.getInventory().setArmorContents(null);
				player.getInventory().clear();
				
				ItemStack compass = new ItemStack(Material.COMPASS);
				CommandUtil.setStackName(compass, ChatColor.GREEN + "Compass (Right Click)");
				player.getInventory().setItem(0, compass);
				
				player.teleport(brSpawn);
			}
			
			private void setWorldBorder(Location center, double buffer, double amount, int diameter, int time)
			{
				WorldBorder border = worldBR.getWorldBorder();
				
				border.setCenter(center);
				border.setDamageBuffer(buffer);
				border.setDamageAmount(amount);
				border.setSize(diameter);
				border.setWarningTime(time);
			}
			
		}, TimeUtil.getMilliseconds(0, 10));
	}
	
	public void endBR()
	{
		purgeTimers();
		
		for (Player player : ServerUtil.getPlayersOnline())
		{
			if (!player.getGameMode().equals(GameMode.CREATIVE))
			{
				player.setGameMode(GameMode.SURVIVAL);
			}
			
			if (!player.getWorld().equals(lobby))
			{
				player.teleport(lobbySpawn);
			}
		}
		
		plugin.isGameActive = false;
	}
	
	private void purgeTimers()
	{
		oneMinWarn.cancel();
		halfMinWarn.cancel();
		tenSecWarn.cancel();
		plugin.timer.cancel();
		
		oneMinWarn.purge();
		halfMinWarn.purge();
		tenSecWarn.purge();
		plugin.timer.purge();
	}
	
	public void checkWin()
	{
		if (plugin.playerListener.getBRPlayers().size() <= 1)
		{
			ServerUtil.say(MessageReference.brEnded);
			ServerUtil.say(MessageReference.winnerIs(plugin.playerListener.getBRPlayers()));
			
			Timer countDownTimer = new Timer();
			Timer winTimer = new Timer();
			
			countDownTimer.schedule(new TimerTask()
			{
				public void run()
				{
					ServerUtil.countdownFrom(10, MessageReference.gameEnding);
				}
			}, gameEndWaitTime - TimeUtil.getMilliseconds(0, 10));
			
			winTimer.schedule(new TimerTask()
			{
				public void run()
				{
					endBR();
				}
			}, gameEndWaitTime);
		}
	}
	
	public void createNewBRWorld()
	{
		int count = 0;
		
		List<World> worlds = Bukkit.getWorlds();
		List<Integer> countList = new ArrayList<Integer>();
		worlds.remove(lobby);
		
		for (World w : worlds)
		{
			if (!w.getName().contains("Lobby"))
			{
				countList.add(Integer.valueOf(w.getName()));
			}
		}
		
		Collections.sort(countList);
		Collections.reverse(countList);
		
		if (countList.size() > 0)
		{
			count = countList.get(0);
		}
		
		count++;
		
		ServerUtil.say(MessageReference.generatingWorld);
		worldBR = Bukkit.createWorld(new WorldCreator(Integer.toString(count)));
	}
	
	public void startWorldBorderShrink(int minDiameter, int time)
	{
		worldBR.getWorldBorder().setSize(minDiameter, time);
	}
}