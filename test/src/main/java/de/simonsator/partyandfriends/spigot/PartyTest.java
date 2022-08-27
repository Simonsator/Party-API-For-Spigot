package de.simonsator.partyandfriends.spigot;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyTest extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent playerChatEvent) {
		Player bukkitPlayer = playerChatEvent.getPlayer();
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(bukkitPlayer.getUniqueId());
		PlayerParty party = PartyManager.getInstance().getParty(player);
		if (party == null) {
			bukkitPlayer.sendMessage("No Party");
			return;
		}
		bukkitPlayer.sendMessage("Leader" + party.getLeader().getName());
		for (PAFPlayer p : party.getPlayers())
			bukkitPlayer.sendMessage("members" + p.getName());
	}

	@Override
	public void onDisable() {
	}
}
