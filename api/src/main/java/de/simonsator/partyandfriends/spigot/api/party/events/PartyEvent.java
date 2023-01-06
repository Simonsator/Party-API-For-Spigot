package de.simonsator.partyandfriends.spigot.api.party.events;

import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.event.Event;

public abstract class PartyEvent extends Event {
	private final PlayerParty PLAYER_PARTY;

	public PartyEvent(PlayerParty pParty) {
		PLAYER_PARTY = pParty;
	}

	public PlayerParty getParty() {
		return PLAYER_PARTY;
	}
}
