package de.simonsator.partyandfriends.spigot.api.events;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;


public interface PartyEventListenerInterface {

	void onLeftPartyEvent(PAFPlayer pPlayer, PlayerParty pParty);

	void onPartyCreatedEvent(PlayerParty pParty);

	void onPartyJoinEvent(PAFPlayer pPlayer, PlayerParty pParty);

	void onPartyLeaderChanged(PAFPlayer pNewLeader, PlayerParty pParty);
}
