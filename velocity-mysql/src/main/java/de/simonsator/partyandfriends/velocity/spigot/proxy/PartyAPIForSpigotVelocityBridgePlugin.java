package de.simonsator.partyandfriends.velocity.spigot.proxy;

import com.velocitypowered.api.event.Subscribe;
import de.simonsator.partyandfriends.velocity.api.PAFExtension;
import de.simonsator.partyandfriends.velocity.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.velocity.api.events.party.LeftPartyEvent;
import de.simonsator.partyandfriends.velocity.api.events.party.PartyCreatedEvent;
import de.simonsator.partyandfriends.velocity.api.events.party.PartyJoinEvent;
import de.simonsator.partyandfriends.velocity.api.events.party.PartyLeaderChangedEvent;
import de.simonsator.partyandfriends.velocity.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.velocity.communication.sql.MySQLData;
import de.simonsator.partyandfriends.velocity.communication.sql.pool.PoolData;
import de.simonsator.partyandfriends.velocity.main.Main;
import de.simonsator.partyandfriends.velocity.pafplayers.mysql.PAFPlayerMySQL;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartyAPIForSpigotVelocityBridgePlugin extends PAFExtension {
	private PartyBridgeVelocityMySQLConnection connection;

	public PartyAPIForSpigotVelocityBridgePlugin(Path folder) {
		super(folder);
	}

	@Override
	public void onEnable() {
		MySQLData mySQLData = new MySQLData(Main.getInstance().getGeneralConfig().get("MySQL.Host").toString(),
				Main.getInstance().getGeneralConfig().get("MySQL.Username").toString(), Main.getInstance().getGeneralConfig().get("MySQL.Password").toString(),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Port"), Main.getInstance().getGeneralConfig().get("MySQL.Database").toString(),
				Main.getInstance().getGeneralConfig().get("MySQL.TablePrefix").toString(), Main.getInstance().getGeneralConfig().getBoolean("MySQL.UseSSL"), Main.getInstance().getGeneralConfig().getBoolean("MySQL.Cache"));
		PoolData poolData = new PoolData(Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.MinPoolSize"),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.MaxPoolSize"),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.InitialPoolSize"), Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.IdleConnectionTestPeriod"), Main.getInstance().getGeneralConfig().getBoolean("MySQL.Pool.TestConnectionOnCheckin"));
		try {
			connection = new PartyBridgeVelocityMySQLConnection(mySQLData, poolData);
			getAdapter().registerListener(this, Main.getInstance());
			registerAsExtension();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		return "Party-MySQL-Proxy-Bridge";
	}

	@Subscribe
	public void onPartyCreateEvent(PartyCreatedEvent pEvent) {
		List<OnlinePAFPlayer> players = pEvent.getParty().getAllPlayers();
		List<Integer> partyMembersIds = new ArrayList<>(players.size());
		for (OnlinePAFPlayer player : players) {
			partyMembersIds.add(((PAFPlayerMySQL) player).getPlayerID());
		}
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.createParty(((PAFPlayerMySQL) pEvent.getParty().getLeader()).getPlayerID(), partyMembersIds));
	}

	@Subscribe
	public void onPartyJoinEvent(PartyJoinEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.joinParty(((PAFPlayerMySQL) pEvent.getParty().getLeader()).getPlayerID(),
						((PAFPlayerMySQL) pEvent.getPlayer()).getPlayerID()))
		;
	}

	@Subscribe
	public void onPartyLeftEvent(LeftPartyEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.leaveParty(((PAFPlayerMySQL) pEvent.getPlayer()).getPlayerID()));
	}

	@Subscribe
	public void onPartyLeaderChangedEvent(PartyLeaderChangedEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(this, () ->
				connection.changePartyLeader(((PAFPlayerMySQL) pEvent.getParty().getLeader()).getPlayerID()));
	}
}
