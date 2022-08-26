package de.simonsator.partyandfriends.spigot.proxy;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface PartyBridgeProxyMySQLConnection {
	String getTablePrefix();

	Connection getConnection();

	void close(Connection con, PreparedStatement pPrepStmt);

	default void importDatabase() throws SQLException {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `" + getTablePrefix() + "party` (`player_member_id` INT(8) NOT NULL, `leader_id` INT(8) NOT NULL);");
			prepStmt.executeUpdate();
		} finally {
			close(con, prepStmt);
		}
	}

	default void cleanTable() {
		try (Statement statement = getConnection().createStatement()) {
			statement.executeUpdate("TRUNCATE TABLE " + getTablePrefix() + "party");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	default void createParty(PlayerParty pParty) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("insert into `" + getTablePrefix() + "party` (`player_member_id`, `leader_id`) values ( ?, ?)");
			int leaderId = ((PAFPlayerMySQL) pParty.getLeader()).getPlayerID();
			for (OnlinePAFPlayer pafPlayer : pParty.getAllPlayers()) {
				prepStmt.setInt(1, ((PAFPlayerMySQL) pafPlayer).getPlayerID());
				prepStmt.setInt(2, leaderId);
				prepStmt.addBatch();
			}
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}
	}

	default void leaveParty(PAFPlayer player) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("DELETE FROM `" + getTablePrefix() + "party` WHERE player_member_id=? LIMIT 1");
			prepStmt.setInt(1, ((PAFPlayerMySQL) player).getPlayerID());
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}
	}

	default void joinParty(OnlinePAFPlayer leader, OnlinePAFPlayer player) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("insert into `" + getTablePrefix() + "party` (`player_member_id`, `leader_id`) values ( ?, ?)");
			prepStmt.setInt(1, ((PAFPlayerMySQL) player).getPlayerID());
			prepStmt.setInt(2, ((PAFPlayerMySQL) leader).getPlayerID());
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}
	}

	default void changePartyLeader(OnlinePAFPlayer newLeader) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			int newLeaderId = ((PAFPlayerMySQL) newLeader).getPlayerID();
			prepStmt = con.prepareStatement("UPDATE `" + getTablePrefix() + "party` SET `leader_id`= ? WHERE `leader_id` = (SELECT `leader_id` FROM (SELECT `leader_id` FROM `" + getTablePrefix() + "party` WHERE `player_member_id` = ? LIMIT 1) AS B)");
			prepStmt.setInt(1, newLeaderId);
			prepStmt.setInt(2, newLeaderId);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}

	}
}
