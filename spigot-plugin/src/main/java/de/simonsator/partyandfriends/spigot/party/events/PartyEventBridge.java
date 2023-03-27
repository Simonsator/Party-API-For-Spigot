package de.simonsator.partyandfriends.spigot.party.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import de.simonsator.partyandfriendsgui.api.PartyFriendsAPI;
import de.simonsator.partyandfriendsgui.communication.tasks.CommunicationTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyEventBridge extends CommunicationTask implements Listener {
	private final List<PartyEventListenerInterface> LISTENERS = new ArrayList<>();
	private final int DELAY = Bukkit.getServer().getPluginManager().getPlugin("PartyAndFriendsGUI").getConfig().
			getInt("General.Compatibility.TicksUntilMessageChannelIsUsedAfterJoin");
	private final Plugin PLUGIN;

	protected PartyEventBridge(Plugin pPlugin) {
		super("PartyEventBridge");
		PLUGIN = pPlugin;
	}

	@Override
	public void executeTask(Player pPlayer, JsonObject pJObj) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(PLUGIN, () -> {
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
				switch (pJObj.get("EventType").getAsString()) {
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
					default:
						System.out.println("Unknown event: " + pJObj.get("EventType").getAsString());
						break;
				}
			}
		}, DELAY);
	}

	public void registerListener(PartyEventListenerInterface pListener) {
		LISTENERS.add(pListener);
		JsonObject jobj = new JsonObject();
		jobj.addProperty("task", "OpenSettingsMenu");
		Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
		if (players.length > 0) {
			sendRegistrationMessage(players[0]);
		}
	}

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent pEvent) {
		if (Bukkit.getOnlinePlayers().size() == 1) {
			Bukkit.getScheduler().runTaskLater(PLUGIN, () -> {
				sendRegistrationMessage(pEvent.getPlayer());
				System.out.println(DELAY);
			}, DELAY);
		}
	}

	private void sendRegistrationMessage(Player pPlayer) {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("task", "PartyEventBridge");
		PartyFriendsAPI.sendMessage(jobj, pPlayer);
	}
}
