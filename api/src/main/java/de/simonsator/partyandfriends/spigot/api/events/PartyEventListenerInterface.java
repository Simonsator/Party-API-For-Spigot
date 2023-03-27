package de.simonsator.partyandfriends.spigot.api.events;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.jetbrains.annotations.Nullable;

/**
 * The interface to receive party events. Please register your listener with {@link PartyEventManager#registerPartyEventListener(PartyEventListenerInterface)}
 */
public interface PartyEventListenerInterface {
	/**
	 * This command will only be called if a party member or the person who left the party, is currently on this spigot server.
	 * Please note that this method is called in an async thread.
	 *
	 * @param pPlayer The player who left the party
	 * @param pParty  The party the player left. If they were the last player or the player deleted the party while this
	 *                event was being bridged to the spigot server, then the party might be null.
	 */
	void onLeftParty(PAFPlayer pPlayer, @Nullable PlayerParty pParty);

	/**
	 * This command will only be called if a party member, is currently on this spigot server.
	 * Please note that this method is called in an async thread.
	 *
	 * @param pParty The party that was created. If the player deleted the party while this event was being bridged to
	 *               the spigot server, then the party might be null.
	 */
	void onPartyCreated(@Nullable PlayerParty pParty);

	/**
	 * This command will only be called if a party member, is currently on this spigot server.
	 * Please note that this method is called in an async thread.
	 *
	 * @param pPlayer The player who joined the party
	 * @param pParty  The party the player joined. If the player deleted the party while this event was being bridged to
	 *                the spigot server, then the party might be null.
	 */
	void onPartyJoin(PAFPlayer pPlayer, @Nullable PlayerParty pParty);

	/**
	 * This command will only be called if a party member, is currently on this spigot server.
	 * Please note that this method is called in an async thread.
	 *
	 * @param pNewLeader The new leader of the party
	 * @param pParty     The party the leader changed for. If the player deleted the party while this event was being bridged to
	 *                   the spigot server, then the party might be null.
	 */
	void onPartyLeaderChanged(PAFPlayer pNewLeader, @Nullable PlayerParty pParty);
}
