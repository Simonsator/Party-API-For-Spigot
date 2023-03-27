package de.simonsator.partyandfriends.spigot.api.events;

/**
 * Thrown when the GUI of Party And Friends is not installed on the Spigot server. It is required for the
 * PartyEventManager, as it is used as a bridge to communicate with the Bungeecord.
 * You can download it from <a href="https://www.spigotmc.org/resources/10123/">here</a>
 */
public class BridgeNotAvailableException extends Exception {
	BridgeNotAvailableException() {
		super("The GUI of Party And Friends is not installed on this Spigot server. It is required in order to use this feature, as it is used as a bridge to communicate with the Bungeecord. You can download it from https://www.spigotmc.org/resources/10123/");
	}
}
