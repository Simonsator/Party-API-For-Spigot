package de.simonsator.partyandfriends.spigot.api.party;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.utilities.disable.Deactivated;
import de.simonsator.partyandfriends.spigot.utilities.disable.Disabler;

/**
 * Manages the parties. From here you can get a {@link de.simonsator.partyandfriends.spigot.api.party.PlayerParty PlayerParty}.
 *
 * @author Simonsator
 */
public abstract class PartyManager implements Deactivated {
	private static PartyManager instance;

	/**
	 * This object should only be created by the server during startup. Don't create a new instance of this object. Use {@link #getInstance()} instead.
	 */
	public PartyManager() {
		instance = this;
		Disabler.getInstance().registerDeactivated(this);
	}

	/**
	 * Returns an instance of this class, which was created during the startup of the server
	 *
	 * @return Returns an instance of this class, which was created during the startup of the server
	 */
	public static PartyManager getInstance() {
		return instance;
	}

	/**
	 * Returns null if the player is not in a party. If the player is in a party (either as the leader or as a party member),
	 * then this function returns a {@link de.simonsator.partyandfriends.spigot.api.party.PlayerParty PlayerParty} representing the party.
	 *
	 * @param pPlayer The player who might be in a party
	 * @return Returns null if the player is not in a party. If the player is in a party (either as the leader or as a party member),
	 * then this function returns a {@link de.simonsator.partyandfriends.spigot.api.party.PlayerParty PlayerParty} representing the party.
	 */
	public abstract PlayerParty getParty(PAFPlayer pPlayer);
}
