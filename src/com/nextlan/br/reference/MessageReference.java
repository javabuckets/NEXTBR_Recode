package com.nextlan.br.reference;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageReference 
{
	/**
	 * Battle Royale
	 */
	// World Generating
	public static final String generatingWorld = ChatColor.DARK_PURPLE + "Generating world... " + ChatColor.RED + "This might cause a small amount of lag";
	
	// BR Info
	public static final String brStarts = ChatColor.DARK_PURPLE + "Battle Royale starts in " + ChatColor.RED + "10 seconds" + ChatColor.DARK_PURPLE + ", prepare yourselves!";
	public static final String brStarting = ChatColor.DARK_PURPLE + "Starting in ";
	public static final String brStarted(ArrayList<Player> brPlayers) { return ChatColor.DARK_PURPLE + "Battle Royale has started with " + ChatColor.GOLD + brPlayers.size() + ChatColor.DARK_PURPLE + " players"; }
	
	public static final String brEnded = ChatColor.DARK_PURPLE + "The battle is over!";
	public static final String winnerIs(ArrayList<Player> brPlayers) { return ChatColor.DARK_GREEN + "The winner is " + ChatColor.GOLD + brPlayers.get(0).getDisplayName(); }
	public static final String gameEnding = ChatColor.DARK_PURPLE + "Ending in ";
	
	// PVP Info
	public static final String pvpEnabledIn = ChatColor.DARK_RED + "PvP" + ChatColor.DARK_PURPLE + " is enabled in " + ChatColor.RED + "2 minutes";
	public static final String pvpOneMinWarn = ChatColor.DARK_RED + "PvP" + ChatColor.DARK_PURPLE + " is enabled in " + ChatColor.RED + "1 minute";
	public static final String pvpHalfMinWarn = ChatColor.DARK_RED + "PvP" + ChatColor.DARK_PURPLE + " is enabled in " + ChatColor.RED + "30 seconds";
	public static final String pvpTenSecCountdown = ChatColor.DARK_RED + "PvP" + ChatColor.DARK_PURPLE + " is enabled in ";
	public static final String pvpEnabled = ChatColor.DARK_RED + "PvP" + ChatColor.DARK_PURPLE + " is now enabled!";
	
	// WorldBorder Info
	public static final String wbNotStartedShrinking = ChatColor.GREEN + "The worldborder hasn't started shrinking yet";
	public static final String wbNeverReach = ChatColor.GREEN + "The border will never reach you";
	public static final String wbReachIn(String min, String sec) { return ChatColor.GREEN + "The border will reach you in " + ChatColor.RED + min + " minutes and " + sec + " seconds"; }
	public static final String wbReachIn(String sec) { return ChatColor.GREEN + "The border will reach you in " + ChatColor.RED + sec + " seconds"; }
	
	/**
	 * Events
	 */
	// PlayerJoinEvent
	public static final String joinLobby(Player p, ArrayList<Player> brPlayers) { return ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_PURPLE +  " joined, " + ChatColor.GOLD + brPlayers.size() + ChatColor.DARK_PURPLE + " players in BR lobby."; }
	public static final String joinAsSpec(Player p) { return ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_PURPLE + " joined the game as a spectator."; }
	public static final String spectateInfo = ChatColor.GREEN + "There is an ongoing Battle Royale! Use /spectate to watch the fight.";
	
	// PlayerQuitEvent
	public static final String playerLeft(Player p, ArrayList<Player> brPlayers) { return ChatColor.DARK_RED + p.getDisplayName() + ChatColor.DARK_PURPLE + " left, " + ChatColor.GOLD + brPlayers.size() + ChatColor.DARK_PURPLE + " players remaining."; }
	
	// PlayerDeathEvent
	public static final String playerKilledBy(Player p, Player killer, ArrayList<Player> brPlayers) { return ChatColor.DARK_RED + p.getDisplayName() + ChatColor.DARK_PURPLE + " was slain by " + ChatColor.GOLD + killer.getDisplayName() + ChatColor.DARK_PURPLE + ", " + ChatColor.GOLD + brPlayers.size() + ChatColor.DARK_PURPLE + " players remaining."; }
	public static final String playerDead(Player p, ArrayList<Player> brPlayers) { return ChatColor.DARK_RED + p.getDisplayName() + ChatColor.DARK_PURPLE + " died, " + ChatColor.GOLD + brPlayers.size() + ChatColor.DARK_PURPLE + " players remaining."; }
	
	// PlayerRespawnEvent
	public static final String lobbyInfo = ChatColor.GREEN + "You can use /lobby to get back to the Lobby.";
	
	/**
	 * Commands
	 */
	
	// AutoFollow
	public static final String autofollowInfo = ChatColor.DARK_PURPLE + "Please specify which Player you would want to automatically follow upon death.";
	public static final String autofollowAlreadyFollowing = ChatColor.DARK_RED + "You are already automatically following this player!";
	public static final String autofollowFollowYourself = ChatColor.DARK_RED + "You cannot follow yourself!";
	public static final String autofollowErrorMsg = ChatColor.DARK_RED + "The specified player is either offline or has been misspelled!";
	public static final String autofollowNowFollowing(Player tp) { return ChatColor.DARK_PURPLE + "You are now following " + tp.getDisplayName(); }
	public static final String autofollowNowFollowing2(Player tp, Player p) { return ChatColor.DARK_PURPLE + "You are now following " + tp.getDisplayName() + ", instead of " + p.getDisplayName(); }
	
	// BRStart
	public static final String brVotestartInfo = ChatColor.DARK_RED + "Use /brvotestart to vote start a new game or talk to a LAN member (5 players needed to vote start!)";
	public static final String brStart1PersonError = ChatColor.RED + "Errors are due to arrive, starting BR game with 1 Player!";
	
	// BRStop
	public static final String noOngoingBR = ChatColor.DARK_RED + "There is no current ongoing Batle Royale.";

	// Lobby
	public static final String lobbyJoinError = ChatColor.DARK_RED + "You cannot do that while in an ongoing Batle Royale!";
	
	// Spectate
	public static final String spectateStupid = ChatColor.DARK_RED + "You are already in spectate mode in the Battle Royale World!...";
	public static final String spectateError = ChatColor.DARK_RED + "There is no current ongoing Batle Royale.";
	public static final String spectateInvalid = ChatColor.DARK_RED + "You can not leave the Battle Royale by using /spectate, either die or disconnect!";
}