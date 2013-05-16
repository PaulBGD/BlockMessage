/*
 * Copyright 2013 ShadowLordAlpha (Bukkit username/pen name)
 * 
 * This file is part of Block Messenger.
 *
 *  Block Messeng is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Block Messeng is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Block Messeng.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gmail.alpha70.shadow;

import java.io.IOException;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockMsg extends JavaPlugin {

	static FileConfiguration config;
	static Server server;
	public void onEnable() {
		config = getConfig();
		server = getServer();
		
		// if you change things in this plugin please keep this message
		getLogger().info("Base BlockMsg created by: shadowlordalpha");
		
			try {
				new UpdateCheck(this);
			} catch (Exception e1) {
				getLogger().warning("Failed To Check For Update!");
			}
		
		config.options().copyDefaults(true);
		config.addDefault("Permissions.sendMsg", true);
		config.addDefault("Updates.updateCheck", true);
		config.addDefault("Permissions.help", "&4You Do Not Have The Permission blockmsg.help!");
		config.addDefault("Permissions.set", "&4You Do Not Have The Permission blockmsg.set!");
		config.addDefault("Permissions.delete", "&4You Do Not Have The Permission blockmsg.delete!");
		config.addDefault("Permissions.delall", "&4You Do Not Have The Permission blockmsg.delall!");
		config.addDefault("Permissions.msg", "&4You Do Not Have The Permission blockmsg.msg!");
		config.addDefault("Permissions.warp", "&4You Do Not Have The Permission blockmsg.warp!");
		config.addDefault("Permissions.cmd", "&4You Do Not Have The Permission blockmsg.cmd!");
		config.addDefault("Permissions.creator", "&4You Do Not Have The Permission blockmsg.creator!");
		config.addDefault("Permissions.list", "&4You Do Not Have The Permission blockmsg.list!");
		config.addDefault("Permissions.start", "&4You Do Not Have The Permission blockmsg.start!");
		config.addDefault("Permissions.stop", "&4You Do Not Have The Permission blockmsg.stop!");
		config.addDefault("Permissions.pos", "&4You Do Not Have The Permission blockmsg.pos!");
		config.addDefault("Permissions.reload", "&4You Do Not Have The Permission blockmsg.reload!");
		
		config.addDefault("Message.Sign.Success", "&2Message set from a Sign!");
		config.addDefault("Message.Sign.Ready", "&2Settin Message from a Sign!");
		config.addDefault("Message.Msg.Success", "&2Message set!");
		config.addDefault("Message.Msg.Fail", "&4Message Not set!");
		config.addDefault("Message.List.Fail", "&4List Fail!");
		config.addDefault("Message.Stop.Success", "&4Commands and Warps not Affection %player%!");
		config.addDefault("Message.Stop.Fail", "&4List Fail!");
		config.addDefault("Message.Start.Success", "&2Commands and Warps now Affecting %player%!");
		config.addDefault("Message.Start.Fail", "&4List Fail!");
		config.addDefault("Message.Reload.Success", "&2[Block Message] Reloaded!");
		config.addDefault("Message.Delall.Success", "&2[Block Message] All Block Messages Deleted!");
		config.addDefault("Message.Delete.Success", "&2Block Message Deleted!");
		config.addDefault("Message.Delete.Fail", "&4Delete Failed!");
		config.addDefault("Message.Warp.SuccessSet", "&2Warp Ready!");
		config.addDefault("Message.Warp.SuccessTo", "&3Warp set!");
		config.addDefault("Message.Cmd.DelSuccess", "&2Command(s) Deleted!");
		config.addDefault("Message.Cmd.AddSuccess", "&2Command(s) Added!");
		config.addDefault("Message.Cmd.Fail", "&4Command Failed!");
		
		
		if (!config.isConfigurationSection("Location")) {
			config.createSection("Location");
		}
				
		getServer().getPluginManager().registerEvents(new BlockMsgListener(this), this);
		saveConfig();
		getCommand("bm").setExecutor(new BlockMsgCmds(this));

		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}

	public void onDisable() {
		reloadConfig();
		saveConfig();
	}
}