package de.simonsator.partyandfriends.spigot.api.events;

/**
 * The PartyEventManager is used to register a listener for party events. It is used as a bridge between the Spigot
 * server and the Bungeecord server. The GUI of Party And Friends is required as it is used as a bridge.
 */
public abstract class PartyEventManager {
	private static PartyEventManager instance;

	protected PartyEventManager() {
		instance = this;
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
	 * Registers a listener for party events. Please call {@link #isBridgeAvailable()} before calling this method,
	 * to make sure that the bridge is available.
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
