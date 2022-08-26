package de.simonsator.partyandfriends.spigot;

import de.simonsator.partyandfriends.spigot.party.redis.RedisPartyManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyAPIForSpigotPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		new RedisPartyManager(getConfig());
	}
}
