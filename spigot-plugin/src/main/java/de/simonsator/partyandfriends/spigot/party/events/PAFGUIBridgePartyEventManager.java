package de.simonsator.partyandfriends.spigot.party.events;

import de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventManager;
import de.simonsator.partyandfriendsgui.communication.BungeecordCommunication;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PAFGUIBridgePartyEventManager extends PartyEventManager {
	private final PartyEventBridge BRIDGE;

	public PAFGUIBridgePartyEventManager(JavaPlugin pPlugin) {
		BRIDGE = new PartyEventBridge(pPlugin);
		BungeecordCommunication.getInstance().registerTask(BRIDGE);
		Bukkit.getPluginManager().registerEvents(BRIDGE, pPlugin);
		setInstance(this);
	}

	@Override
	protected void internalRegisterPartyEventListener(PartyEventListenerInterface pListener) {
		BRIDGE.registerListener(pListener);
	}
}
