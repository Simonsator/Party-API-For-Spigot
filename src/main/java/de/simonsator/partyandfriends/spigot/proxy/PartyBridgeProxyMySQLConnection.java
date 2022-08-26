package de.simonsator.partyandfriends.spigot.proxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface PartyBridgeProxyMySQLConnection {
	String getTablePrefix();

	Connection getConnection();

	void close(Connection con, PreparedStatement pPrepStmt);

	default void importDatabase() throws SQLException {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `" + getTablePrefix() +
					"party` (`player_member_id` INT(8) NOT NULL, `leader_id` INT(8) NOT NULL, PRIMARY KEY (`player_member_id`));");
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

	default void createParty(int leaderId, List<Integer> memberIds) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("insert into `" + getTablePrefix() + "party` (`player_member_id`, `leader_id`) values ( ?, ?)");
			for (int memberId : memberIds) {
				prepStmt.setInt(1, memberId);
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

	default void leaveParty(int pPlayerId) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("DELETE FROM `" + getTablePrefix() + "party` WHERE player_member_id=? LIMIT 1");
			prepStmt.setInt(1, pPlayerId);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}
	}

	default void joinParty(int pLeaderId, int pPlayerId) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("insert IGNORE into `" + getTablePrefix() + "party` (`player_member_id`, `leader_id`) values ( ?, ?) ");
			prepStmt.setInt(1, pLeaderId);
			prepStmt.setInt(2, pPlayerId);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}
	}

	default void changePartyLeader(int newLeaderId) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
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
