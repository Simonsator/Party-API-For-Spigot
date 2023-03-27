package de.simonsator.partyandfriends.spigot.api.party;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;

import java.util.List;
import java.util.UUID;

/**
 * Represents a party with players
 *
 * @author Simonsator
 */
public abstract class PlayerParty {
	protected PlayerParty() {
	}

	/**
	 * Returns true if the player is a member of the party. Returns false if the player is not part of this party or if the player is the party leader.
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player is a member of the party. Returns false if the player is not part of this party or if the player is the party leader.
	 */
	public abstract boolean isAMember(PAFPlayer pPlayer);

	protected abstract List<UUID> getInvited();

	/**
	 * Returns the party leader
	 *
	 * @return Returns the party leader
	 */
	public abstract PAFPlayer getLeader();

	/**
	 * Returns a list of all the players in this party who are not the party leader.
	 *
	 * @return Returns a list of all the players in this party who are not the party leader.
	 */
	public abstract List<PAFPlayer> getPlayers();

	/**
	 * Returns true if currently nobody is invited into the party. Returns false if at least one person invited into this party.
	 *
	 * @return Returns true if currently nobody is invited into the party. Returns false if at least one person invited into this party.
	 */
	public boolean isNobodyInvited() {
		return getInvited().isEmpty();
	}

	/**
	 * Returns all players in this party (including the party leader).
	 *
	 * @return Returns all players in this party (including the party leader).
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

	/**
	 * Returns true if the given player is the leader of this party,
	 * and false if he is not the leader, of this party
	 *
	 * @param player The player
	 * @return Returns true if the given player is the leader of this party,
	 * and false if he is not the leader, of this party
	 */
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
	public boolean isInvited(PAFPlayer pPlayer) {
		return getInvited().contains(pPlayer.getUniqueId());
	}
}
