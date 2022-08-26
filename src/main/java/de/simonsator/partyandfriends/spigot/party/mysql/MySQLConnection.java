package de.simonsator.partyandfriends.spigot.party.mysql;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.communication.sql.MySQLData;
import de.simonsator.partyandfriends.spigot.communication.sql.SQLCommunication;
import de.simonsator.partyandfriends.spigot.pafplayers.mysql.PAFPlayerMySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnection extends SQLCommunication {
	private final String TABLE_PREFIX;

	public MySQLConnection(MySQLData pMySQLData) {
		super(pMySQLData.DATABASE, "jdbc:mysql://" + pMySQLData.HOST + ":" + pMySQLData.PORT, pMySQLData.USERNAME, pMySQLData.PASSWORD, pMySQLData.USE_SSL);
		this.TABLE_PREFIX = pMySQLData.TABLE_PREFIX;
	}

	public MySQLPlayerParty getParty(int pPlayerId) {
		PAFPlayer leader = null;
		List<PAFPlayer> members = new ArrayList<>();
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select `player_member_id`, `leader_id` from `" + DATABASE +
					"`.`" + TABLE_PREFIX + "party` WHERE leader_id= (SELECT leader_id FROM  `" + DATABASE + "`.`" + TABLE_PREFIX
					+ "party` WHERE player_member_id = '" + pPlayerId + "'LIMIT 1 )");
			while (rs.next()) {
				int memberId = rs.getInt("player_member_id");
				int leaderId = rs.getInt("leader_id");
				if (memberId != leaderId) {
					members.add(new PAFPlayerMySQL(memberId));
				} else {
					leader = new PAFPlayerMySQL(leaderId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		if (leader == null)
			return null;
		return new MySQLPlayerParty(leader, members);
	}
}
