package de.simonsator.partyandfriends.velocity.spigot.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import de.simonsator.partyandfriends.velocity.VelocityExtensionLoadingInfo;
import de.simonsator.partyandfriends.velocity.main.PAFPlugin;

import java.nio.file.Path;

@Plugin(id = "party-proxy-bridge-for-paf", name = "Party Proxy Bridge for Party and Friends", version = "1.0.3-SNAPSHOT",
		description = "Loads Party Proxy Bridge for Party and Friends", authors = {"Simonsator"}, dependencies = {@Dependency(id = "partyandfriends")})
public class PartyAPISpigotBridgeLoader {
	private final Path folder;

	@Inject
	public PartyAPISpigotBridgeLoader(@DataDirectory final Path folder) {
		this.folder = folder;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		PAFPlugin.loadExtension(new VelocityExtensionLoadingInfo(new PartyAPIForSpigotVelocityBridgePlugin(folder),
				"party-proxy-bridge-for-paf", "Loads Party Proxy Bridge for Party and Friends", "1.0.3-SNAPSHOT", "Simonsator"));
	}

}
