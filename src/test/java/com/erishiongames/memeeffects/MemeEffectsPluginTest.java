package com.erishiongames.memeeffects;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class MemeEffectsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(MemeEffectsPlugin.class);
		RuneLite.main(args);
	}
}