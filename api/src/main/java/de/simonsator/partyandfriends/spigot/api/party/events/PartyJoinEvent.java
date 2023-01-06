package de.simonsator.partyandfriends.spigot.api.party.events;


import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.event.HandlerList;

public class PartyJoinEvent extends PartyEvent {
	private final PAFPlayer PLAYER;
	private static final HandlerList HANDLERS = new HandlerList();

	public PartyJoinEvent(PlayerParty pParty, PAFPlayer pPlayer) {
		super(pParty);
		PLAYER = pPlayer;
	}

	public PAFPlayer getPlayer() {
		return PLAYER;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
