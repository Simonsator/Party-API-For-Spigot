package de.simonsator.partyandfriends.spigot;

import de.simonsator.partyandfriends.spigot.error.ErrorReporter;
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
			case "none":
				new ErrorReporter("§cThe plugin has not been setup yet. Please visit https://github.com/Simonsator/Party-API-For-Spigot/wiki/Installation to learn how to setup the plugin.");
				break;
			default:
				new ErrorReporter("§cPartyDataTransferMethod must be either \"redis\" or \"mysql\". Please refer to the installation guide (https://github.com/Simonsator/Party-API-For-Spigot/wiki/Installation) for more information.");
		}
	}
}
