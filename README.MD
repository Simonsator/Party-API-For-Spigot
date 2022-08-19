# Party-API-For-Spigot

Provides an API for spigot plugins to access information about Parties
from [Party and Friends Extended Edition for Bungeecord/Velocity](https://www.spigotmc.org/resources/party-and-friends-extended-edition-for-bungeecord-velocity-supports-1-7-1-19.10123/)
. For more information on how to install the plugin please refer
to [it's spigotmc description](https://www.spigotmc.org/resources/spigot-party-api-for-party-and-friends-extended-redisbungee-required.39751/)
.

## Alternatives

If you don't want to use RedisBungee you can
use [this alternative api](https://github.com/Simonsator/Example-For-Spigot-Party-Data-Callback-API)

## How to use

```
PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(pPlayer.getUniqueId());
PlayerParty party = PartyManager.getInstance().getParty(pafPlayer);
if (party != null) {
	// The party exists. You can use different methods of the PlayerParty object to get information about the party, e.g. who is in the party.
} else {
	// The player is not in a party
}
```

## Maven

```
<repositories>
	<repository>
		<id>simonsators-repo</id>
		<url>https://simonsator.de/repo/</url>
	</repository>
</repositories>
<dependencies>
	<dependency>
		<groupId>de.simonsator</groupId>
		<artifactId>Party-and-Friends-MySQL-Edition-Spigot-API</artifactId>
		<version>1.5.3</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>de.simonsator</groupId>
		<artifactId>Spigot-Party-API-For-RedisBungee</artifactId>
		<version>1.0.3-SNAPSHOT</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

