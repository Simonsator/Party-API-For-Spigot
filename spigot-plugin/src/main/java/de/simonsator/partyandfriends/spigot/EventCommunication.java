package de.simonsator.partyandfriends.spigot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import de.simonsator.partyandfriends.spigot.api.party.events.LeftPartyEvent;
import de.simonsator.partyandfriends.spigot.api.party.events.PartyCreatedEvent;
import de.simonsator.partyandfriends.spigot.api.party.events.PartyJoinEvent;
import de.simonsator.partyandfriends.spigot.api.party.events.PartyLeaderChangedEvent;
import de.simonsator.partyandfriendsgui.communication.tasks.CommunicationTask;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class EventCommunication extends CommunicationTask {
    protected EventCommunication() {
        super("Events");
    }

    @Override
    public void executeTask(Player player, JsonObject jsonObject) {
        switch (jsonObject.get("EventName").getAsString()) {
            case "LeftPartyEvent": {
                PlayerParty party = null;
                for (JsonElement jsonElement: jsonObject.getAsJsonArray("Players")) {
                    UUID UUID = java.util.UUID.fromString(jsonElement.getAsString());
                    if (getPAFParty(Objects.requireNonNull(UUID)) != null)
                        party = getPAFParty(Objects.requireNonNull(UUID));
                }
                new LeftPartyEvent(party, PAFPlayerManager.getInstance().getPlayer(player.getUniqueId()));
                break;
            }
            case "PartyCreatedEvent": {
                new PartyCreatedEvent(getPAFParty(player));
                break;
            }
            case "PartyJoinEvent": {
                new PartyJoinEvent(getPAFParty(player), PAFPlayerManager.getInstance().getPlayer(player.getUniqueId()));
                break;
            }
            case "PartyLeaderChangedEvent": {
                new PartyLeaderChangedEvent(getPAFParty(player), PAFPlayerManager.getInstance().getPlayer(player.getUniqueId()));
                break;
            }
        }
    }

    private PlayerParty getPAFParty(Player p) {
        return PartyManager.getInstance().getParty(PAFPlayerManager.getInstance().getPlayer(p.getUniqueId()));
    }

    private PlayerParty getPAFParty(UUID p) {
        return PartyManager.getInstance().getParty(PAFPlayerManager.getInstance().getPlayer(p));
    }
}
