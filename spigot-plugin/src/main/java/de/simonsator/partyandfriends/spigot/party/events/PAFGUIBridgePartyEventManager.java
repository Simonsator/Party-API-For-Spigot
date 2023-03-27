package de.simonsator.partyandfriends.spigot.party.events;

import de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface;
import de.simonsator.partyandfriends.spigot.api.events.PartyEventManager;
import de.simonsator.partyandfriendsgui.communication.BungeecordCommunication;

public class PAFGUIBridgePartyEventManager extends PartyEventManager {
	private final PartyEventBridge BRIDGE = new PartyEventBridge();

	public PAFGUIBridgePartyEventManager() {
		BungeecordCommunication.getInstance().registerTask(BRIDGE);
		setInstance(this);
	}

	@Override
	protected void internalRegisterPartyEventListener(PartyEventListenerInterface pListener) {
		BRIDGE.registerListener(pListener);
	}
}
