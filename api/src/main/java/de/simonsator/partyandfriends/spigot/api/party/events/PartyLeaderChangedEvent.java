package de.simonsator.partyandfriends.spigot.api.party.events;


import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.event.HandlerList;

public class PartyLeaderChangedEvent extends PartyEvent {
	private final PAFPlayer NEW_LEADER;

	private static final HandlerList HANDLERS = new HandlerList();

	public PartyLeaderChangedEvent(PlayerParty pParty, PAFPlayer pNewLeader) {
		super(pParty);
		NEW_LEADER = pNewLeader;
	}

	public PAFPlayer getNewLeader() {
		return NEW_LEADER;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
