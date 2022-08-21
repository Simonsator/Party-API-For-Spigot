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
 */
public class PlayerParty {
	private final int ID;

	PlayerParty(int pID) {
		ID = pID;
	}

	/**
	 * Returns true if the player is a member of the party. Returns false if the player is not part of this party or if the player is the party leader.
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player is a member of the party. Returns false if the player is not part of this party or if the player is the party leader.
	 */
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

	/**
	 * Returns the party leader
	 *
	 * @return Returns the party leader
	 */
	public PAFPlayer getLeader() {
		return PAFPlayerManager.getInstance().getPlayer(getLeaderUUID());
	}

	/**
	 * Returns a list of all the players in this party who are not the party leader.
	 *
	 * @return Returns a list of all the players in this party who are not the party leader.
	 */
	public List<PAFPlayer> getPlayers() {
		List<PAFPlayer> players = new ArrayList<>();
		Jedis jedis = PartyManager.getInstance().getConnection();
		for (String uuid : jedis.lrange("paf:parties:" + ID + ":players", 0, 1000)) {
			players.add(PAFPlayerManager.getInstance().getPlayer(UUID.fromString(uuid)));
		}
		jedis.close();
		return players;
	}

	/**
	 * Returns true if currently nobody is invited into the party. Returns false if at least one person invited into this party.
	 *
	 * @return Returns true if currently nobody is invited into the party. Returns false if at least one person invited into this party.
	 */
	@SuppressWarnings("unused")
	public boolean isNobodyInvited() {
		return getInvited().isEmpty();
	}

	/**
	 * Returns all players in this party (including the party leader).
	 *
	 * @return Returns all players in this party (including the party leader).
	 */
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
	public boolean isInParty(PAFPlayer pPlayer) {
		return isAMember(pPlayer) || pPlayer.getUniqueId().equals(getLeader().getUniqueId());
	}

	/**
	 * Returns true if the given player is the leader of this party,
	 * and false if he is not the leader, of this party
	 *
	 * @param player The player
	 * @return Returns true if the given player is the leader of this party,
	 * and false if he is not the leader, of this party
	 */
	@SuppressWarnings("unused")
	public boolean isLeader(PAFPlayer player) {
		return getLeader() != null && player != null && this.getLeader().getUniqueId().equals(player.getUniqueId());
	}

	/**
	 * Returns true if the player is already invited. Returns false if
	 * the player is not invited
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player is already invited. Returns false if
	 * the player is not invited.
	 */
	@SuppressWarnings("unused")
	public boolean isInvited(PAFPlayer pPlayer) {
		return getInvited().contains(pPlayer.getUniqueId());
	}
}
