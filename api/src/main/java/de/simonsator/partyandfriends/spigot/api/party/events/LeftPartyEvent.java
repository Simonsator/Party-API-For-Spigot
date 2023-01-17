package de.simonsator.partyandfriends.spigot.api.party.events;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This is event is called when a player left a party (no matter if they disconnect from the server, gets kicked or just uses /party leave).
 * At this point no new leader was chosen yet. If the leader left the party the leader of the party will now be null.
 */
public class LeftPartyEvent extends PartyEvent {
	private final PAFPlayer PLAYER;

	private static final HandlerList HANDLERS = new HandlerList();

	public LeftPartyEvent(PlayerParty pParty, PAFPlayer pPlayer) {
		super(pParty);
		PLAYER = pPlayer;
	}

	@SuppressWarnings("unused")
	public PAFPlayer getPlayer() {
		return PLAYER;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLERS;
	}
}
