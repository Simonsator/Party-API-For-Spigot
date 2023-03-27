package de.simonsator.partyandfriends.spigot.api.events;

public abstract class PartyEventManager {
	private static PartyEventManager instance;

	protected static void setInstance(PartyEventManager pInstance) {
		instance = pInstance;
	}

	public static boolean isBridgeAvailable() {
		return instance != null;
	}

	public static void registerPartyEventListener(PartyEventListenerInterface pListener) throws BridgeNotAvailableException {
		if (instance == null)
			throw new BridgeNotAvailableException();
		instance.internalRegisterPartyEventListener(pListener);
	}

	protected abstract void internalRegisterPartyEventListener(PartyEventListenerInterface pListener);
}
