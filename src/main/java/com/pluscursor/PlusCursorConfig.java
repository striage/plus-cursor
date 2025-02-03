package com.pluscursor;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("PlusCursor")
public interface PlusCursorConfig extends Config
{
	@ConfigItem(
		keyName = "hideCursor",
		name = "Hide cursor",
		description = "Hides the default cursor"
	)
	default boolean hideCursor() { return true; }

	@ConfigItem(
		keyName = "interactColor",
		name = "Interact color",
		description = "Color when hovering over something interactable"
	)
	default Color interactColor() { return Color.RED; }

	@ConfigItem(
		keyName = "defaultColor",
		name = "Default color",
		description = "Colour when hovering over the floor, or nothing"
	)
	default Color defaultColor() { return Color.CYAN; }
}
