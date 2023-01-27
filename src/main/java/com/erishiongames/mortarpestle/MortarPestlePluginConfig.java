package com.erishiongames.mortarpestle;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface MortarPestlePluginConfig extends Config
{
	@ConfigItem(
		keyName = "Volume",
		name = "Volume",
		description = "Adjust how loud the sound effect is",
		position = 0
	)

	default int volume()
	{
		return 100;
	}
}
