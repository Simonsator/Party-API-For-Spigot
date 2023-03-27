package de.simonsator.partyandfriends.spigot;

import de.simonsator.partyandfriends.spigot.api.events.BridgeNotAvailableException;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventManager;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public class PartyTest extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		if (PartyEventManager.isBridgeAvailable()) {
			try {
				PartyEventManager.registerPartyEventListener(new PartyEventListenerInterface() {
					private final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

					@Override
					public void onLeftParty(PAFPlayer pPlayer, @Nullable PlayerParty pParty) {
						console.sendMessage("Left party" + pPlayer.getName());
						printParty(console, pParty);
					}

					@Override
					public void onPartyCreated(@Nullable PlayerParty pParty) {
						console.sendMessage("Created party");
						printParty(console, pParty);

					}

					@Override
					public void onPartyJoin(PAFPlayer pPlayer, @Nullable PlayerParty pParty) {
						console.sendMessage("Joined party" + pPlayer.getName());
						printParty(console, pParty);
					}

					@Override
					public void onPartyLeaderChanged(PAFPlayer pNewLeader, @Nullable PlayerParty pParty) {
						console.sendMessage("Leader changed party" + pNewLeader.getName());
						printParty(console, pParty);
					}
				});
			} catch (BridgeNotAvailableException e) {
				e.printStackTrace();
			}
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage("§cNo Bridge");
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent playerChatEvent) {
		Player bukkitPlayer = playerChatEvent.getPlayer();
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(bukkitPlayer.getUniqueId());
		PlayerParty party = PartyManager.getInstance().getParty(player);
		printParty(bukkitPlayer, party);
	}

	private void printParty(CommandSender pReceiver, PlayerParty pParty) {
		if (pParty == null) {
			pReceiver.sendMessage("No Party");
			return;
		}
		pReceiver.sendMessage("Leader: " + pParty.getLeader().getName());
		for (PAFPlayer p : pParty.getPlayers())
			pReceiver.sendMessage("Members: " + p.getName());
	}

	@Override
	public void onDisable() {
	}
}
