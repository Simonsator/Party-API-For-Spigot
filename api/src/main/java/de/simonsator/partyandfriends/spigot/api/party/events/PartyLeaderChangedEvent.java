package de.simonsator.partyandfriends.spigot.api.party.events;


import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PartyLeaderChangedEvent extends PartyEvent {
	private final PAFPlayer NEW_LEADER;

	private static final HandlerList HANDLERS = new HandlerList();

	public PartyLeaderChangedEvent(PlayerParty pParty, PAFPlayer pNewLeader) {
		super(pParty);
		NEW_LEADER = pNewLeader;
	}

	@SuppressWarnings("unused")
	public PAFPlayer getNewLeader() {
		return NEW_LEADER;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLERS;
	}
}