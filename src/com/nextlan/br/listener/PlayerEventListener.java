package com.nextlan.br.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.nextlan.br.NEXTBR;
import com.nextlan.br.reference.MessageReference;
import com.nextlan.br.utility.ServerUtil;

public class PlayerEventListener implements Listener
{
	private NEXTBR plugin;

	public PlayerEventListener(NEXTBR plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerConnect(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		if (plugin.isGameActive)
		{
			player.setGameMode(GameMode.SPECTATOR);
			event.setJoinMessage(MessageReference.joinAsSpec(player));
			player.sendMessage(MessageReference.spectateInfo);
		}
		else if (player.getGameMode().equals(GameMode.SURVIVAL))
		{
			event.setJoinMessage(MessageReference.joinLobby(player, getBRPlayers()));
		}
		else
		{
			event.setJoinMessage(null);
		}
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		if (plugin.isGameActive)
		{
			if (player.getGameMode().equals(GameMode.SURVIVAL))
			{
				player.setGameMode(GameMode.SPECTATOR);
				player.getInventory().clear();

				ServerUtil.say(MessageReference.playerLeft(player, getBRPlayers()));

				plugin.br.checkWin();
			}

			event.setQuitMessage(null);
		}
	}

	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event)
	{
		if (event.getEntity().getWorld().equals(plugin.br.lobby))
		{
			event.setCancelled(true);
		}

		if (!plugin.isGameActive)
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();

		player.setGameMode(GameMode.SPECTATOR);
		player.getInventory().clear();

		if (player.getKiller() instanceof Player)
		{
			event.setDeathMessage(MessageReference.playerKilledBy(player, player.getKiller(), getBRPlayers()));
		}
		else
		{
			event.setDeathMessage(MessageReference.playerDead(player, getBRPlayers()));
		}

		plugin.br.checkWin();
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(plugin.br.brSpawn);
		event.getPlayer().sendMessage(MessageReference.lobbyInfo);
		
		if (plugin.autofollow.autoFollowMap.containsKey(event.getPlayer()))
		{
			// Set event.getPlayer() to spectate the autofollowed Player.
		}
	}

	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event)
	{
		if (event.getBlock().getWorld().equals(plugin.br.lobby))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerBuildBlock(BlockPlaceEvent event)
	{
		if (event.getBlock().getWorld().equals(plugin.br.lobby))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPickUpItem(PlayerPickupItemEvent event)
	{
		if (event.getPlayer().getWorld().equals(plugin.br.lobby))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		if (event.getPlayer().getWorld().equals(plugin.br.lobby))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerEnterBed(PlayerBedEnterEvent event)
	{
		event.setCancelled(true);
	}

	@EventHandler
	public void onItemRightClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if (plugin.isGameActive)
		{
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				if (player.getItemInHand().getType() == Material.COMPASS)
				{
					WorldBorder worldborder = this.plugin.br.worldBR.getWorldBorder();
					Location playerPos = player.getLocation();

					float blocksPerSecond = 0.5F;
					int borderSize = (int)worldborder.getSize() / 2;
					List<Integer> distances = new ArrayList<Integer>();

					if (!this.plugin.br.worldBR.getPVP())
					{
						player.sendMessage(MessageReference.wbNotStartedShrinking);
					}
					else if ((playerPos.getX() < plugin.br.endDiameter/2) && (playerPos.getX() > -plugin.br.endDiameter/2) && (playerPos.getZ() < plugin.br.endDiameter/2) && (playerPos.getZ() > -plugin.br.endDiameter/2))
					{
						player.sendMessage(MessageReference.wbNeverReach);
					}
					else
					{
						if (playerPos.getX() > 0.0D)
						{
							distances.add(Integer.valueOf((int)(borderSize - playerPos.getX())));
						}
						else
						{
							distances.add(Integer.valueOf((int)(borderSize + playerPos.getX())));
						}

						if (playerPos.getZ() > 0.0D)
						{
							distances.add(Integer.valueOf((int)(borderSize - playerPos.getZ())));
						}
						else
						{
							distances.add(Integer.valueOf((int)(borderSize + playerPos.getZ())));
						}

						Collections.sort(distances);

						int timeSeconds = (int)(((Integer)distances.get(0)).intValue() / blocksPerSecond);

						if (timeSeconds > 60)
						{
							int seconds = timeSeconds % 60;
							int minutes = timeSeconds / 60;

							player.sendMessage(MessageReference.wbReachIn(Integer.toString(minutes), Integer.toString(seconds)));
						}
						else
						{
							player.sendMessage(MessageReference.wbReachIn(Integer.toString(timeSeconds)));
						}
					}
				}
			}
		}
	}

	/**
	 * Returns an ArrayList<Player> for each player in GameMode SURVIVAL
	 * All Players are included. OP's should be GameMode CREATIVE at all times.
	 */
	public ArrayList<Player> getBRPlayers()
	{
		ArrayList<Player> players = new ArrayList<Player>();

		for (int i = 0; i < ServerUtil.getPlayersOnline().size(); i++)
		{
			if (ServerUtil.getPlayersOnline().toArray(new Player[0])[i].getGameMode() == GameMode.SURVIVAL)
			{
				players.add(ServerUtil.getPlayersOnline().toArray(new Player[0])[i]);
			}
		}

		return players;
	}
}