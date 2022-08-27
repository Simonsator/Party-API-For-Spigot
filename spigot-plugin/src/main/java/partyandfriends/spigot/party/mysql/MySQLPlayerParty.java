package partyandfriends.spigot.party.mysql;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQLPlayerParty extends PlayerParty {
	private final PAFPlayer LEADER;
	private final List<PAFPlayer> MEMBERS;

	MySQLPlayerParty(PAFPlayer leader, List<PAFPlayer> members) {
		LEADER = leader;
		MEMBERS = members;
	}

	@Override
	public boolean isAMember(PAFPlayer pPlayer) {
		return MEMBERS.contains(pPlayer);
	}

	@Override
	protected List<UUID> getInvited() {
		System.out.println("Spigot Party API using MySQL does not support invited players. Please use Redis");
		return new ArrayList<>();
	}

	@Override
	public PAFPlayer getLeader() {
		return LEADER;
	}

	@Override
	public List<PAFPlayer> getPlayers() {
		return MEMBERS;
	}
}
