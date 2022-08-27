package de.simonsator.partyandfriends.spigot.party.mysql;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import de.simonsator.partyandfriends.spigot.communication.sql.MySQLData;
import de.simonsator.partyandfriends.spigot.pafplayers.mysql.PAFPlayerMySQL;

public class MySQLPartyManager extends PartyManager {
	private final MySQLConnection CONNECTION;

	public MySQLPartyManager(MySQLData mySQLData) {
		CONNECTION = new MySQLConnection(mySQLData);
	}

	@Override
	public PlayerParty getParty(PAFPlayer pPlayer) {
		return CONNECTION.getParty(((PAFPlayerMySQL) pPlayer).getPlayerID());
	}

	@Override
	public void onDisable() {
		CONNECTION.closeConnection();
	}
}
