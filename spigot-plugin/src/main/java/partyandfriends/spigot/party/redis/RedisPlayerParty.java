package partyandfriends.spigot.party.redis;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RedisPlayerParty extends PlayerParty {
	private final int ID;
	private final RedisPartyManager REDIS_PARTY_MANAGER;

	RedisPlayerParty(int pID, RedisPartyManager pPartyManager) {
		ID = pID;
		REDIS_PARTY_MANAGER = pPartyManager;
	}

	@Override
	public boolean isAMember(PAFPlayer pPlayer) {
		try (Jedis jedis = REDIS_PARTY_MANAGER.getConnection()) {
			return jedis.lrange("paf:parties:" + ID + ":players", 0, 1000).contains(pPlayer.getUniqueId().toString());
		}
	}

	@Override
	protected List<UUID> getInvited() {
		Jedis jedis = REDIS_PARTY_MANAGER.getConnection();
		List<String> list = jedis.lrange("paf:parties:" + ID + ":invited", 0, 1000);
		jedis.close();
		List<UUID> uuids = new ArrayList<>();
		for (String uuid : list)
			uuids.add(UUID.fromString(uuid));
		return uuids;
	}

	private UUID getLeaderUUID() {
		Jedis jedis = REDIS_PARTY_MANAGER.getConnection();
		String uuid = jedis.hget("paf:parties:" + ID + ":properties", "leader");
		jedis.close();
		if (uuid == null)
			return null;
		return UUID.fromString(uuid);
	}

	@Override
	public PAFPlayer getLeader() {
		return PAFPlayerManager.getInstance().getPlayer(getLeaderUUID());
	}

	@Override
	public List<PAFPlayer> getPlayers() {
		List<PAFPlayer> players = new ArrayList<>();
		Jedis jedis = REDIS_PARTY_MANAGER.getConnection();
		for (String uuid : jedis.lrange("paf:parties:" + ID + ":players", 0, 1000)) {
			PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(UUID.fromString(uuid));
			players.add(player);
		}
		jedis.close();
		return players;
	}
}
