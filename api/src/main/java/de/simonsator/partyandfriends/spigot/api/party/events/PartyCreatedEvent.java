package de.simonsator.partyandfriends.spigot.api.party.events;


import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.event.HandlerList;

public class PartyCreatedEvent extends PartyEvent {
	private static final HandlerList HANDLERS = new HandlerList();
	public PartyCreatedEvent(PlayerParty pParty) {
		super(pParty);
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
