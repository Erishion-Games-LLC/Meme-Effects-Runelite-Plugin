package com.erishiongames.memesounds;

import com.erishiongames.memesounds.MemeSoundsPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class MemeSoundsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(MemeSoundsPlugin.class);
		RuneLite.main(args);
	}
}