package com.pluscursor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

@Slf4j
public class PlusCursorOverlay extends Overlay
{
	private final Client client;

	@Inject
	public PlusCursorOverlay(Client client)
	{
		this.client = client;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Point mouse = client.getMouseCanvasPosition();
		if (mouse != null)
		{
			int x = mouse.getX();
			int y = mouse.getY();
			int screenHeight = client.getCanvasHeight();
			int screenWidth = client.getCanvasWidth();

			MenuEntry[] menuEntries = client.getMenu().getMenuEntries();

			if (menuEntries.length > 0)
			{
				MenuAction topEntryType = menuEntries[menuEntries.length - 1].getType();
				if (topEntryType == MenuAction.WALK ||
				topEntryType == MenuAction.CANCEL)
				{
					graphics.setColor(Color.CYAN);
				}
				else
				{
					graphics.setColor(Color.RED);
				}
			} else {
				graphics.setColor(Color.WHITE);
			}

			graphics.drawLine(x, 0, x, screenHeight);
			graphics.drawLine(0, y, screenWidth, y);
		}

		return null;
	}
}
