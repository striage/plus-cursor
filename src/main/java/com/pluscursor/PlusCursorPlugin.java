package com.pluscursor;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Plus Cursor"
)
public class PlusCursorPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PlusCursorOverlay plusCursorOverlay;

	@Override
	protected void startUp() throws Exception
	{
		log.info("starting up");
		overlayManager.add(plusCursorOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("shutting down");
		overlayManager.remove(plusCursorOverlay);
	}

	@Provides
	PlusCursorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PlusCursorConfig.class);
	}
}
