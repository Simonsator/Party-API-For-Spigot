package de.simonsator.partyandfriends.spigot.party.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import de.simonsator.partyandfriendsgui.communication.tasks.CommunicationTask;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyEventBridge extends CommunicationTask {
	private final List<PartyEventListenerInterface> LISTENERS = new ArrayList<>();

	protected PartyEventBridge() {
		super("PartyEventBridge");
	}

	@Override
	public void executeTask(Player pPlayer, JsonObject pJObj) {
		PAFPlayer player = null;
		if (pJObj.has("Player")) {
			player = PAFPlayerManager.getInstance().getPlayer(UUID.fromString(pJObj.get("Player").getAsString()));
		}
		PlayerParty party = null;
		for (JsonElement partyMember : pJObj.get("AllPartyMembers").getAsJsonArray()) {
			PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(UUID.fromString(partyMember.getAsString()));
			if (pafPlayer != null) {
				party = PartyManager.getInstance().getParty(pafPlayer);
				if (party != null) {
					break;
				}
			}
		}
		for (PartyEventListenerInterface listener : LISTENERS) {
			switch (pJObj.get("").getAsString()) {
				case "LeftPartyEvent":
					listener.onLeftParty(player, party);
					break;
				case "PartyCreatedEvent":
					listener.onPartyCreated(party);
					break;
				case "PartyJoinEvent":
					listener.onPartyJoin(player, party);
					break;
				case "PartyLeaderChangedEvent":
					listener.onPartyLeaderChanged(player, party);
					break;
			}
		}
	}

	public void registerListener(PartyEventListenerInterface pListener) {
		LISTENERS.add(pListener);
	}
}
