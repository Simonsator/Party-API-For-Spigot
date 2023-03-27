package de.simonsator.partyandfriends.spigot.api.events;

public abstract class PartyEventManager {
	private static PartyEventManager instance;

	protected static void setInstance(PartyEventManager pInstance) {
		instance = pInstance;
	}

	/**
	 * Checks if the bridge is available
	 *
	 * @return True if the bridge is available, false if not.
	 */
	public static boolean isBridgeAvailable() {
		return instance != null;
	}

	/**
	 * Registers a listener for party events
	 *
	 * @param pListener The listener Which will be registered
	 * @throws BridgeNotAvailableException If the bridge is not available. This means that the GUI of Party And Friends
	 *                                     is not installed on this spigot server. The GUI is required as it used as a
	 *                                     bridge between the Spigot server and Bungeecord server.
	 */
	public static void registerPartyEventListener(PartyEventListenerInterface pListener) throws BridgeNotAvailableException {
		if (instance == null)
			throw new BridgeNotAvailableException();
		instance.internalRegisterPartyEventListener(pListener);
	}

	protected abstract void internalRegisterPartyEventListener(PartyEventListenerInterface pListener);
}
