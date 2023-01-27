package com.erishiongames;

import com.erishiongames.mortarpestle.MortarPestlePlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class MortarPestlePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(MortarPestlePlugin.class);
		RuneLite.main(args);
	}
}