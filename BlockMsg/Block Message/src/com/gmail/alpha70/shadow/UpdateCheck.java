package com.gmail.alpha70.shadow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.event.Listener;

public class UpdateCheck implements Listener {
	// The name of your plugin, as specified in the plugin.yml
	public String pluginName = "BlockMsg";

	// Your plugin's BukkitDev URL. DON'T FORGET THE SLASH AT THE END
	public URL bukkitURL = new URL("http://dev.bukkit.org/server-mods/block-message/files.rss");

	// The message that will be sent to a player if the player is an op and
	// joins the server when there's a new update out
	public String notifyMessage = "There is a new version of " + pluginName
			+ " out! Download it at http://dev.bukkit.org/server-mods/block-message/";

	public UpdateCheck(BlockMsg main) throws Exception {
		
		if (main.getConfig().getBoolean("Updates.updateCheck", true)) {
		
		URLConnection connection = bukkitURL.openConnection();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		   String line = null, data = "";

		   while ((line = reader.readLine()) != null) {
		      data += line + " ";
		   }
		   
		   String work[] = data.split("<title>");
		   String other[] = work[2].split("</title>");
		   String placeHolder = other[0].replace("Block Message ", "");
		   String[] test = placeHolder.replace(".", ":").split(":");
		   String[] test2 = main.getDescription().getVersion().replace(".", ":").split(":");
		   int a,b,c,d,e,f;
		   
		   a = Integer.parseInt(test[0]);
		   b = Integer.parseInt(test[1]);
		   c = Integer.parseInt(test[2]);
		   d = Integer.parseInt(test2[0]);
		   e = Integer.parseInt(test2[1]);
		   f = Integer.parseInt(test2[2]);
		   
		   boolean isOutOfDate = false;
		   
		   if (a >= d) {
			   if (a > d) {
				   isOutOfDate = true;
			   }
			   if (b >= e) {
				   if (b > e) {
					   isOutOfDate = true;
				   }
				   if (c > f) {
					   isOutOfDate = true;
				   }
			   }
		   } 
		   
		   
			   if (isOutOfDate) {
				  
				   main.getLogger().warning(notifyMessage);
			   }
		   }
	}
}
