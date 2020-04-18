package de.simonsator.partyandfriends.spigot.api.party;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.utilities.disable.Deactivated;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

/**
 * Manages the parties. From here you can get a PlayerParty.
 *
 * @author Simonsator
 * @version 1.0.0 19.04.17
 */
public class PartyManager extends JavaPlugin implements Deactivated {
	private static PartyManager instance;

	public PartyManager() {
		instance = this;
	}

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public static PartyManager getInstance() {
		return instance;
	}

	Jedis getConnection() {
		Jedis jedis = new Jedis(getConfig().getString("Redis.Host"), getConfig().getInt("Redis.Port"));
		if (!getConfig().getString("Redis.Password").equals(""))
			jedis.auth(getConfig().getString("Redis.Password"));
		return jedis;
	}

	public PlayerParty getParty(PAFPlayer pPlayer) {
		try (Jedis jedis = getConnection()) {
			String id = jedis.get("paf:parties:players:" + pPlayer.getUniqueId() + ":id");
			if (id == null)
				return null;
			return new PlayerParty(Integer.parseInt(id));
		}
	}
}
