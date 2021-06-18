package de.simonsator.partyandfriends.spigot.api.party;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a party with players
 *
 * @author Simonsator
 * @version 1.0.0 19.04.17
 */
public class PlayerParty {
	private final int ID;

	public PlayerParty(int pID) {
		ID = pID;
	}

	public boolean isAMember(PAFPlayer pPlayer) {
		try (Jedis jedis = PartyManager.getInstance().getConnection()) {
			return jedis.lrange("paf:parties:" + ID + ":players", 0, 1000).contains(pPlayer.getUniqueId().toString());
		}
	}

	protected List<UUID> getInvited() {
		Jedis jedis = PartyManager.getInstance().getConnection();
		List<String> list = jedis.lrange("paf:parties:" + ID + ":invited", 0, 1000);
		jedis.close();
		List<UUID> uuids = new ArrayList<>();
		for (String uuid : list)
			uuids.add(UUID.fromString(uuid));
		return uuids;
	}

	private UUID getLeaderUUID() {
		Jedis jedis = PartyManager.getInstance().getConnection();
		String uuid = jedis.hget("paf:parties:" + ID + ":properties", "leader");
		jedis.close();
		if (uuid == null)
			return null;
		return UUID.fromString(uuid);
	}

	public PAFPlayer getLeader() {
		return PAFPlayerManager.getInstance().getPlayer(getLeaderUUID());
	}

	public List<PAFPlayer> getPlayers() {
		List<PAFPlayer> players = new ArrayList<>();
		Jedis jedis = PartyManager.getInstance().getConnection();
		for (String uuid : jedis.lrange("paf:parties:" + ID + ":players", 0, 1000)) {
			PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(UUID.fromString(uuid));
			players.add(player);
		}
		jedis.close();
		return players;
	}

	public boolean isNobodyInvited() {
		return getInvited().isEmpty();
	}

	/**
	 * @return Returns all players in this party (inclusive the leader).
	 */
	public List<PAFPlayer> getAllPlayers() {
		List<PAFPlayer> allPlayers = getPlayers();
		PAFPlayer leader = getLeader();
		if (leader != null)
			allPlayers.add(leader);
		return allPlayers;
	}

	/**
	 * Returns true if the player is in the party. Returns false if the player
	 * is not in the party.
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player is in the party. Returns false if the
	 * player is not in the party.
	 */
	public boolean isInParty(PAFPlayer pPlayer) {
		return isAMember(pPlayer) || pPlayer.getUniqueId().equals(getLeader().getUniqueId());
	}

	public boolean isLeader(PAFPlayer player) {
		return getLeader() != null && player != null && this.getLeader().getUniqueId().equals(player.getUniqueId());
	}

	public boolean isInvited(PAFPlayer pPlayer) {
		return getInvited().contains(pPlayer.getUniqueId());
	}
}
