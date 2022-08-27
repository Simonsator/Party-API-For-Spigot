package de.simonsator.partyandfriends.spigot.proxy.bungee;

import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.communication.sql.pool.PoolData;
import de.simonsator.partyandfriends.communication.sql.pool.PoolSQLCommunication;
import de.simonsator.partyandfriends.spigot.proxy.PartyBridgeProxyMySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartyBridgeBungeeMySQLConnection extends PoolSQLCommunication implements PartyBridgeProxyMySQLConnection {
	private final String TABLE_PREFIX;

	public PartyBridgeBungeeMySQLConnection(MySQLData pMySQLData, PoolData pPoolData) throws SQLException {
		super(pMySQLData, pPoolData);
		this.TABLE_PREFIX = pMySQLData.TABLE_PREFIX;
		importDatabase();
		cleanTable();
	}

	@Override
	public String getTablePrefix() {
		return TABLE_PREFIX;
	}

	@Override
	public void close(Connection con, PreparedStatement pPrepStmt) {
		super.close(con, pPrepStmt);
	}
}
