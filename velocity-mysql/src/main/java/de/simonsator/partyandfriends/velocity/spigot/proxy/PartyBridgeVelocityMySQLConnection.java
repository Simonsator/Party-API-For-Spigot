package de.simonsator.partyandfriends.velocity.spigot.proxy;

import de.simonsator.partyandfriends.spigot.proxy.PartyBridgeProxyMySQLConnection;
import de.simonsator.partyandfriends.velocity.communication.sql.MySQLData;
import de.simonsator.partyandfriends.velocity.communication.sql.pool.PoolData;
import de.simonsator.partyandfriends.velocity.communication.sql.pool.PoolSQLCommunication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PartyBridgeVelocityMySQLConnection extends PoolSQLCommunication implements PartyBridgeProxyMySQLConnection {
	private final String TABLE_PREFIX;

	public PartyBridgeVelocityMySQLConnection(MySQLData pMySQLData, PoolData pPoolData) throws SQLException {
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
