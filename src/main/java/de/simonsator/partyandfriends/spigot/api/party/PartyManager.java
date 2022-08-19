package de.simonsator.partyandfriends.spigot.api.party;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.utilities.disable.Deactivated;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

/**
 * Manages the parties. From here you can get a {@link de.simonsator.partyandfriends.spigot.api.party.PlayerParty PlayerParty}.
 *
 * @author Simonsator
 */
public class PartyManager extends JavaPlugin implements Deactivated {
	private static PartyManager instance;

	/**
	 * This object should only be created by the server during startup. Don't create a new instance of this object. Use {@link #getInstance()} instead.
	 */
	public PartyManager() {
		instance = this;
	}

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	/**
	 * Returns an instance of this class, which was created during the startup of the server
	 *
	 * @return Returns an instance of this class, which was created during the startup of the server
	 */
	public static PartyManager getInstance() {
		return instance;
	}

	Jedis getConnection() {
		Jedis jedis = new Jedis(getConfig().getString("Redis.Host"), getConfig().getInt("Redis.Port"));
		if (!getConfig().getString("Redis.Password").equals(""))
			jedis.auth(getConfig().getString("Redis.Password"));
		return jedis;
	}

	/**
	 * Returns null if the player is not in a party. If the player is in a party (either as the leader or as a party member),
	 * then this function returns a {@link de.simonsator.partyandfriends.spigot.api.party.PlayerParty PlayerParty} representing the party.
	 *
	 * @param pPlayer The player who might be in a party
	 * @return Returns null if the player is not in a party. If the player is in a party (either as the leader or as a party member),
	 * then this function returns a {@link de.simonsator.partyandfriends.spigot.api.party.PlayerParty PlayerParty} representing the party.
	 */
	public PlayerParty getParty(PAFPlayer pPlayer) {
		try (Jedis jedis = getConnection()) {
			String id = jedis.hget("paf:parties:players:id", pPlayer.getUniqueId().toString());
			if (id == null)
				return null;
			return new PlayerParty(Integer.parseInt(id));
		}
	}
}
