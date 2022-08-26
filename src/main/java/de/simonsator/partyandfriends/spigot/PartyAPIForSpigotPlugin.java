package de.simonsator.partyandfriends.spigot;

import de.simonsator.partyandfriends.spigot.main.Main;
import de.simonsator.partyandfriends.spigot.party.mysql.MySQLPartyManager;
import de.simonsator.partyandfriends.spigot.party.redis.RedisPartyManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class PartyAPIForSpigotPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		switch (Objects.requireNonNull(getConfig().getString("PartyDataTransferMethod")).toLowerCase()) {
			case "redis":
				new RedisPartyManager(getConfig());
				break;
			case "mysql":
				new MySQLPartyManager(Main.getInstance().getMySQLData());
				break;
			default:
				throw new IllegalArgumentException("PartyDataTransferMethod must be either \"redis\" or \"mysql\"");
		}
	}
}
