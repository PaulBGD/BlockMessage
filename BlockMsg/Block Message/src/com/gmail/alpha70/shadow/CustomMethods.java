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

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomMethods {

	public static String[] setCubeWithin(Location a, Location b) {
		int x1, y1, z1 ,x2, y2, z2, x3, y3, z3;
		x1 = a.getBlockX();
		y1 = a.getBlockY();
		z1 = a.getBlockZ();
		x2 = b.getBlockX();
		y2 = b.getBlockY();
		z2 = b.getBlockZ();
		if (x1 < x2) {
			x3 = x1;
			x1 = x2;
			x2 = x3;
		}
		if (y1 < y2) {
			y3 = y1;
			y1 = y2;
			y2 = y3;
		}
		if (z1 < z2) {
			z3 = z1;
			z1 = z2;
			z2 = z3;
		}	
		String s1 = a.getWorld().getName() + "," + x1 + "," + y1 + "," + z1;
		String s2 = b.getWorld().getName() + "," + x2 + "," + y2 + "," + z2;
		return new String[] {s1, s2};
	}
	
	public static Location returnLocation(String path) {
		FileConfiguration config = BlockMsg.config;
		Server server = BlockMsg.server;
		String s = config.getString(path);
		String[] as = s.split(",", 4);
		World world = server.getWorld(as[0]);
		double x = Double.parseDouble(as[1]);
		double y = Double.parseDouble(as[2]);
		double z = Double.parseDouble(as[3]);
		Location l = new Location(world, x, y, z);
		return l;
	}
	
	public static String warplocation(Location loc) {
		String s = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getYaw() + "," + 0;
		return s;
	}
	
	public static Location returnWarp(String path) {
		FileConfiguration config = BlockMsg.config;
		Server server = BlockMsg.server;
		String s = config.getString(path);
		String[] as = s.split(",", 6);
		World world = server.getWorld(as[0]);
		double x = Double.parseDouble(as[1]);
		double y = Double.parseDouble(as[2]);
		double z = Double.parseDouble(as[3]);
		int pitch = (int) Double.parseDouble(as[4]);
		int yaw = (int) Double.parseDouble(as[5]);
		Location l = new Location( world, x + .5, y, z + .5, pitch, yaw);
		return l;
	}
	
	public static Boolean isWithin(Location player, String path) {
			Location pos1 = returnLocation(path + ".pos1");
			Location pos2 = returnLocation(path + ".pos2");
			if (player.getBlockX() <= pos1.getBlockX() && player.getBlockX() >= pos2.getBlockX()) {
				if (player.getBlockY() <= pos1.getBlockY() && player.getBlockY() >= pos2.getBlockY()) {
					if (player.getBlockZ() <= pos1.getBlockZ() && player.getBlockZ() >= pos2.getBlockZ()) {
						return true;
					}
				}
			}
		return false;
	}
	
	public static Boolean isWithin(Location player, Location pos1, Location pos2) {
		if (player.getBlockX() <= pos1.getBlockX() && player.getBlockX() >= pos2.getBlockX()) {
			if (player.getBlockY() <= pos1.getBlockY() && player.getBlockY() >= pos2.getBlockY()) {
				if (player.getBlockZ() <= pos1.getBlockZ() && player.getBlockZ() >= pos2.getBlockZ()) {
					return true;
				}
			}
		}
	return false;
}
}
