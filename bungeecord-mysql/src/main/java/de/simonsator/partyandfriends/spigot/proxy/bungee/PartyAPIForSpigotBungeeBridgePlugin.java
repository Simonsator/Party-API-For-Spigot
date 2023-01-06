package de.simonsator.partyandfriends.spigot.proxy.bungee;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.party.LeftPartyEvent;
import de.simonsator.partyandfriends.api.events.party.PartyCreatedEvent;
import de.simonsator.partyandfriends.api.events.party.PartyJoinEvent;
import de.simonsator.partyandfriends.api.events.party.PartyLeaderChangedEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.communication.sql.pool.PoolData;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartyAPIForSpigotBungeeBridgePlugin extends PAFExtension implements Listener {
	private PartyBridgeBungeeMySQLConnection connection;

	@Override
	public void onEnable() {
		MySQLData mySQLData = new MySQLData(Main.getInstance().getGeneralConfig().get("MySQL.Host").toString(),
				Main.getInstance().getGeneralConfig().get("MySQL.Username").toString(), Main.getInstance().getGeneralConfig().get("MySQL.Password").toString(),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Port"), Main.getInstance().getGeneralConfig().get("MySQL.Database").toString(),
				Main.getInstance().getGeneralConfig().get("MySQL.TablePrefix").toString(), Main.getInstance().getGeneralConfig().getBoolean("MySQL.UseSSL"), Main.getInstance().getGeneralConfig().getBoolean("MySQL.Cache"));
		PoolData poolData = new PoolData(Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.MinPoolSize"),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.MaxPoolSize"),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.InitialPoolSize"), Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.IdleConnectionTestPeriod"), Main.getInstance().getGeneralConfig().getBoolean("MySQL.Pool.TestConnectionOnCheckin"), "C3P0");
		try {
			connection = new PartyBridgeBungeeMySQLConnection(mySQLData, poolData);
			getAdapter().registerListener(this, this);
			registerAsExtension();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@EventHandler
	public void onPartyCreateEvent(PartyCreatedEvent pEvent) {
		List<OnlinePAFPlayer> players = pEvent.getParty().getAllPlayers();
		List<Integer> partyMembersIds = new ArrayList<>(players.size());
		for (OnlinePAFPlayer player : players) {
			partyMembersIds.add(((PAFPlayerMySQL) player).getPlayerID());
		}
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.createParty(((PAFPlayerMySQL) pEvent.getParty().getLeader()).getPlayerID(), partyMembersIds));
	}

	@EventHandler
	public void onPartyJoinEvent(PartyJoinEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.joinParty(((PAFPlayerMySQL) pEvent.getParty().getLeader()).getPlayerID(),
						((PAFPlayerMySQL) pEvent.getPlayer()).getPlayerID()));
	}

	@EventHandler
	public void onPartyLeftEvent(LeftPartyEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.leaveParty(((PAFPlayerMySQL) pEvent.getPlayer()).getPlayerID()));
	}

	@EventHandler
	public void onPartyLeaderChangedEvent(PartyLeaderChangedEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.changePartyLeader(((PAFPlayerMySQL) pEvent.getParty().getLeader()).getPlayerID()));
	}
}
