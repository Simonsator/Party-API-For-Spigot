package de.simonsator.spigot;

import de.simonsator.partyandfriends.spigot.error.ErrorReporter;
import de.simonsator.partyandfriends.spigot.main.Main;
import de.simonsator.spigot.party.mysql.MySQLPartyManager;
import de.simonsator.spigot.party.redis.RedisPartyManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class PartyAPIForSpigotPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		File file = new File(getDataFolder(), "config.yml");
		try {
			Path path = file.toPath();
			if (!file.exists()) {
				InputStream in = getResource(file.getName());
				OutputStream out = Files.newOutputStream(path);
				byte[] buf = new byte[1024];
				int len;
				while ((len = Objects.requireNonNull(in).read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
			}
			getConfig().load(new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8));
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
