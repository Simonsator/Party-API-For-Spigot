package de.simonsator.spigot.party.redis;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.configuration.Configuration;
import redis.clients.jedis.Jedis;

public class RedisPartyManager extends PartyManager {
	private final Configuration CONFIG;

	public RedisPartyManager(Configuration pConfig) {
		CONFIG = pConfig;
	}

	Jedis getConnection() {
		Jedis jedis = new Jedis(CONFIG.getString("Redis.Host"), CONFIG.getInt("Redis.Port"));
		if (!CONFIG.getString("Redis.Password").equals(""))
			jedis.auth(CONFIG.getString("Redis.Password"));
		return jedis;
	}

	@Override
	public PlayerParty getParty(PAFPlayer pPlayer) {
		try (Jedis jedis = getConnection()) {
			String id = jedis.hget("paf:parties:players:id", pPlayer.getUniqueId().toString());
			if (id == null)
				return null;
			return new RedisPlayerParty(Integer.parseInt(id), this);
		}
	}

	@Override
	public void onDisable() {

	}
}
