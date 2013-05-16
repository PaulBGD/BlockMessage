/*
 * Copyright 2013 ShadowLordAlpha (Bukkit username/pen name)
 * 
 * This file is part of Block Messenger.
 *
 *  Block Messenger is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Block Messenger is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Block Messenger.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gmail.alpha70.shadow;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BlockMsgListener implements Listener {
	
	BlockMsg plugin;
	FileConfiguration config;
	HashMap<String, String> lastMessage = new HashMap<String, String>();
	HashMap<String, String> lastCommand = new HashMap<String, String>();
	static HashMap<String, String> signLocation = new HashMap<String, String>();
	static ArrayList<String> stop = new ArrayList<String>();

	public BlockMsgListener(BlockMsg main) {
		plugin = main;
		config = main.getConfig();
	}
	
	@EventHandler
	public void onPlayerExit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		plugin.getLogger().info("Removing: " + player.getDisplayName());
		signLocation.remove(player.getName());
		lastMessage.remove(player.getName());
		lastCommand.remove(player.getName());
		BlockMsgCmds.warpLocation.remove(player.getName());
		stop.remove(player.getName());
		return;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(signLocation.containsKey(player.getName())) {
			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (event.getClickedBlock().getState() instanceof Sign) {
					Sign getMsg = (Sign) event.getClickedBlock().getState();
					String msg = getMsg.getLine(0) + " " + getMsg.getLine(1) + " " + getMsg.getLine(2) + " " + getMsg.getLine(3);
					plugin.getConfig().set("Location." + signLocation.get(player.getName()) + ".message", msg);
					plugin.getConfig().set("Location." + signLocation.get(player.getName()) + ".messageCreator", player.getName());
					plugin.saveConfig();
					String mesg = config.getString("Message.set.success");
					mesg = msg.replace("%player%", player.getName());
					mesg = ChatColor.translateAlternateColorCodes('&', mesg);
					player.sendMessage(msg);
					signLocation.remove(player.getName());
					String msg1 = config.getString("Message.Sign.Success");
					msg1 = msg1.replace("%player%", player.getDisplayName());
					msg1 = ChatColor.translateAlternateColorCodes('&', msg1);
					player.sendMessage(msg1);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		String fromLocation = event.getFrom().getWorld().toString() + event.getFrom().getBlockX() + event.getFrom().getBlockY() + event.getFrom().getBlockZ();
		String toLocation = event.getTo().getWorld().toString() + event.getTo().getBlockX() + event.getTo().getBlockY() + event.getTo().getBlockZ();
		boolean b = true;
			if (!fromLocation.equalsIgnoreCase(toLocation)) {
					
				for (String s : config.getConfigurationSection("Location.").getKeys(false)) {
					if (CustomMethods.isWithin(event.getTo(), "Location." + s)) {
						b = false;
						if (config.getString("Location." + s + ".message") != null) {
							String msg = config.getString("Location." + s + ".message");
							if (lastMessage.get(player.getName()) != null) {
								if (!lastMessage.get(player.getName()).equalsIgnoreCase(msg)) {
									lastMessage.put(player.getName(), msg);
									msg = msg.replace("%player%", player.getDisplayName());
									msg = ChatColor.translateAlternateColorCodes('&', msg);
									player.sendMessage(msg);
								}
							} else {
								lastMessage.put(player.getName(), msg);
								msg = msg.replace("%player%", player.getDisplayName());
								msg = ChatColor.translateAlternateColorCodes('&', msg);
								player.sendMessage(msg);
							}
						}
						if (!stop.contains(player.getName())) {
							if (config.getString("Location." + s + ".command") != null) {
								String cmd = config.getString("Location." + s + ".command");
								if (lastCommand.get(player.getName()) != null) {
									if (!lastCommand.get(player.getName()).equalsIgnoreCase(cmd)) {
										lastCommand.put(player.getName(), cmd);
										String[] cmda = cmd.split("<!!!>");
										for (String st : cmda) {
											st = st.replace("<!!!>", "");
											if (st.contains("%sender%")) {
												st = st.replace("%sender% ", "").replace("%sender%", "").replace("%player%", player.getDisplayName());
												Bukkit.dispatchCommand(player, st);
											}
											if (st.contains("%console%")) {
												st = st.replace("%console% ", "").replace("%console%", "").replace("%player%", player.getDisplayName());
												Bukkit.dispatchCommand(Bukkit.getConsoleSender(), st);
											}
										}
									} 
								} else {
									lastCommand.put(player.getName(), cmd);
									String[] cmda = cmd.split("<!!!>");
									for (String st : cmda) {
										st = st.replace("<!!!>", "");
										if (st.contains("%sender%")) {
											st = st.replace("%sender% ", "").replace("%sender%", "").replace("%player%", player.getDisplayName());
											plugin.getServer().dispatchCommand(player, st);
										}
										if (st.contains("%console%")) {
											st = st.replace("%console% ", "").replace("%console%", "").replace("%player%", player.getDisplayName());
											plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), st);
										}
									}
								}
							}
							if (config.getString("Location." + s + ".warp") != null) {
								Location location = CustomMethods.returnWarp("Location." + s + ".warp");
								player.teleport(location);
							}
						}
					}
				}
				
				if (b) {
					lastMessage.remove(player.getName());
					lastCommand.remove(player.getName());
					return;
				}
			}
	}
}
