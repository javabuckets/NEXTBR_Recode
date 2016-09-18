package com.nextlan.br;

import java.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nextlan.br.command.AutoFollow;
import com.nextlan.br.command.BRPause;
import com.nextlan.br.command.BRRestart;
import com.nextlan.br.command.BRStart;
import com.nextlan.br.command.BRStop;
import com.nextlan.br.command.BRVotestart;
import com.nextlan.br.command.Lobby;
import com.nextlan.br.command.Spectate;
import com.nextlan.br.listener.EventListener;
import com.nextlan.br.listener.PlayerEventListener;

public class NEXTBR extends JavaPlugin
{
	public PlayerEventListener playerListener = new PlayerEventListener(this);
	public EventListener eventListener = new EventListener(this);
	
	public BRStart brstart = new BRStart(this);
	public BRStop brstop = new BRStop(this);
	public BRRestart brrestart = new BRRestart(this);
	public BRPause brpause = new BRPause(this);
	public BRVotestart brvotestart = new BRVotestart(this);
	public Spectate spectate = new Spectate(this);
	public Lobby lobby = new Lobby(this);
	public AutoFollow autofollow = new AutoFollow(this); // Recode AutoFollow using UUID instead of Player.
	
	public BattleRoyale br = new BattleRoyale(this);

	// Battle Royale Timer
	public Timer timer = new Timer();
	
	public boolean isGameActive = false;
	
	@Override
	public void onEnable()
	{	
		eventInit();
		commandInit();
		tabCompleterInit();
	}
	
	private void eventInit()
	{
		PluginManager manager = Bukkit.getServer().getPluginManager();
		
		manager.registerEvents(playerListener, this);
		manager.registerEvents(eventListener, this);
	}
	
	private void commandInit()
	{
		getCommand("brstart").setExecutor(brstart);
		getCommand("brstop").setExecutor(brstop);
		getCommand("brrestart").setExecutor(brrestart);
		getCommand("brpause").setExecutor(brpause);
		getCommand("brvotestart").setExecutor(brvotestart);
		
		getCommand("spectate").setExecutor(spectate);
		getCommand("lobby").setExecutor(lobby);
		getCommand("autofollow").setExecutor(autofollow);
	}

	private void tabCompleterInit() 
	{
		getCommand("autofollow").setTabCompleter(autofollow);
	}
	
	public void reloadTimer()
	{
		timer = new Timer();
	}
}