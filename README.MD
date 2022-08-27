# Party API for Spigot Plugins for Party and Friends

Provides an API for Spigot plugins to access information about Parties
from [Party and Friends Extended Edition for Bungeecord/Velocity](https://www.spigotmc.org/resources/party-and-friends-extended-edition-for-bungeecord-velocity-supports-1-7-1-19.10123/)
, [Party and Friends for BungeeCord](https://www.spigotmc.org/resources/party-and-friends-for-bungeecord-supports-1-7-x-to-1-19-x.9531/)
and [Party and Friends Velocity](https://forums.papermc.io/threads/party-and-friends-for-velocity-version-1-0-87.317/).

For more information on how to install the plugin please refer
to [the installation guide](https://github.com/Simonsator/Party-API-For-Spigot/wiki/Installation).

## Download

You may download a compiled version of the plugin from
[spigotmc.org](https://www.spigotmc.org/resources/spigot-party-api-for-party-and-friends-extended-redisbungee-required.39751/)
.

# For Developers

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

## Maven Dependency

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
		<artifactId>spigot-party-api-for-party-and-friends</artifactId>
		<version>1.0.3-SNAPSHOT</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

### Alternatives

You may also use the [callback api(https://github.com/Simonsator/Example-For-Spigot-Party-Data-Callback-API) to retrieve
party data

## How to build

To build this plugin use maven. The final output jar can be found in "final-build/target/PartyAPIForSpigotPlugins.jar".