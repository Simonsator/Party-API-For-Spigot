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

### Getting the party of a player

```java
PAFPlayer pafPlayer=PAFPlayerManager.getInstance().getPlayer(pPlayer.getUniqueId());
PlayerParty party=PartyManager.getInstance().getParty(pafPlayer);
if(party!=null){
    // The party exists. You can use different methods of the PlayerParty object to get information about the party, e.g. who is in the party.
}else{
    // The player is not in a party
}
```

### Using Events

This plugin does not use the normal Bukkit events. Instead, you have to create a class
implementing [PartyEventListenerInterface](https://simonsator.de/?dir=JavaDoc/PartyRedisBungeeAPIForSpigot/de/simonsator/partyandfriends/spigot/api/events/PartyEventListenerInterface.html)
and register it
using [PartyEventManager.registerPartyEventListener(PartyEventListenerInterface pListener)](https://simonsator.de/?dir=JavaDoc/PartyRedisBungeeAPIForSpigot/de/simonsator/partyandfriends/spigot/api/events/PartyEventManager.html#registerPartyEventListener(de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface)).

When an event is fired, the corresponding method of your class will be called.

The event system only works if the [GUI of Party And Friends](https://www.spigotmc.org/resources/10123/) is installed on
the Spigot server, as it is used as a bridge to transfer event data from the proxy to the Spigot server. If it is not
installed [PartyEventManager.registerPartyEventListener(PartyEventListenerInterface pListener)](https://simonsator.de/?dir=JavaDoc/PartyRedisBungeeAPIForSpigot/de/simonsator/partyandfriends/spigot/api/events/PartyEventManager.html#registerPartyEventListener(de.simonsator.partyandfriends.spigot.api.events.PartyEventListenerInterface))
will throw an exception. To check if the GUI is installed you can use the
method [PartyEventManager.isBridgeAvailable()](https://simonsator.de/?dir=JavaDoc/PartyRedisBungeeAPIForSpigot/de/simonsator/partyandfriends/spigot/api/events/PartyEventManager.html#isBridgeAvailable()).

#### Example

```java
if (PartyEventManager.isBridgeAvailable()) {
	try {
		PartyEventManager.registerPartyEventListener(new PartyEventListenerInterface() {
			private final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

			@Override
			public void onLeftParty(PAFPlayer pPlayer, @Nullable PlayerParty pParty) {
				console.sendMessage("Left party" + pPlayer.getName());
			}

			@Override
			public void onPartyCreated(@Nullable PlayerParty pParty) {
				console.sendMessage("Created party");
			}

			@Override
			public void onPartyJoin(PAFPlayer pPlayer, @Nullable PlayerParty pParty) {
				console.sendMessage("Joined party" + pPlayer.getName());
			}

			@Override
			public void onPartyLeaderChanged(PAFPlayer pNewLeader, @Nullable PlayerParty pParty) {
				console.sendMessage("Leader changed party" + pNewLeader.getName());
			}
		});
	} catch (BridgeNotAvailableException e) {
		e.printStackTrace();
	}
} else {
	Bukkit.getServer().getConsoleSender().sendMessage("§cThe Party And Friends main plugin is not installed on this spigot server. Please download it from https://www.spigotmc.org/resources/10123/");
}
```

You can find the JavaDoc [here](https://simonsator.de/JavaDoc/PartyRedisBungeeAPIForSpigot/index.html).

## Maven Dependency

```xml
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
	<version>1.5.4-RELEASE</version>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>de.simonsator</groupId>
	<artifactId>spigot-party-api-for-party-and-friends</artifactId>
	<version>1.0.5-RELEASE</version>
	<scope>provided</scope>
</dependency>
</dependencies>
```

### Alternatives

You may also use the [callback api](https://github.com/Simonsator/Example-For-Spigot-Party-Data-Callback-API) to
retrieve
party data

## How to build

To build this plugin use maven. The final output jar can be found in "final-build/target/PartyAPIForSpigotPlugins.jar".
To test your changes you may use the plugin "test/target/spigot-party-api-test-VERSION.jar". This will output the names
of the players in the party of the person who send a chat message. 
