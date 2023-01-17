package de.simonsator.partyandfriends.spigot.api.party.events;


import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PartyJoinEvent extends PartyEvent {
	private final PAFPlayer PLAYER;
	private static final HandlerList HANDLERS = new HandlerList();

	public PartyJoinEvent(PlayerParty pParty, PAFPlayer pPlayer) {
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
