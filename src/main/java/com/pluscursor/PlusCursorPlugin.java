package com.pluscursor;

import com.google.inject.Provides;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientUI;
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

	@Inject
	private PlusCursorConfig config;

	@Inject
	private ClientUI clientUI;

	@Inject
	private Client client;

	private Cursor oldCursor;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(plusCursorOverlay);
		this.oldCursor = client.getCanvas().getCursor();
		updateCursor();
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(plusCursorOverlay);
		revertToOldCursor();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		log.info("config changed");
		if (event.getGroup().equals("PlusCursor") && event.getKey().equals("hideCursor"))
		{
			updateCursor();
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			updateCursor();
		}
	}

	private void updateCursor()
	{
		GameState gameState = client.getGameState();
		if (gameState == GameState.UNKNOWN ||
			gameState == GameState.STARTING ||
			gameState == GameState.LOGIN_SCREEN ||
			gameState == GameState.LOGIN_SCREEN_AUTHENTICATOR ||
			gameState == GameState.LOGGING_IN ||
			gameState == GameState.LOADING
		)
		{
			return;
		}

		if (config.hideCursor())
		{
			this.oldCursor = client.getCanvas().getCursor();
			hideCursor();
		}
		else
		{
			revertToOldCursor();
		}
	}

	private void hideCursor()
	{
		BufferedImage transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Cursor invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			transparentImage, new Point(0, 0), "InvisibleCursor");
		client.getCanvas().setCursor(invisibleCursor);
	}

	private void revertToOldCursor()
	{
		if (this.oldCursor != null)
		{
			client.getCanvas().setCursor(this.oldCursor);
		}
		else
		{
			clientUI.resetCursor();
		}
	}

	@Provides
	PlusCursorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PlusCursorConfig.class);
	}
}
