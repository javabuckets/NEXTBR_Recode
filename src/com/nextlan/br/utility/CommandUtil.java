package com.nextlan.br.utility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandUtil 
{
	public static List<String> getTabCompletePlayers()
	{
		List<String> list = new ArrayList<String>();
		
		for (Player p : ServerUtil.getPlayersOnline())
		{
			list.add(p.getDisplayName());
		}
		
		return list;
	}
	
	public static ItemStack setStackName(ItemStack itemstack, String name)
	{
		ItemMeta itemMeta = itemstack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemstack.setItemMeta(itemMeta);
		return itemstack;
	}
}