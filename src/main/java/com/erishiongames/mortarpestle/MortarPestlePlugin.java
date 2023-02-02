package com.erishiongames.mortarpestle;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.OkHttpClient;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@PluginDescriptor(
	name = "Mortar Pestle"
)
public class MortarPestlePlugin extends Plugin
{
	private boolean playSound = false;

	@Inject
	private Client client;

	@Inject
	private MortarPestlePluginConfig config;

	@Inject
	private ScheduledExecutorService executor;

	@Inject
	private OkHttpClient okHttpClient;

	@Inject
	private SoundEngine soundEngine;


	@Override
	protected void startUp() throws Exception
	{

	}

	@Override
	protected void shutDown() throws Exception
	{

	}

	@Subscribe
	public void onSoundEffectPlayed(SoundEffectPlayed soundEffectPlayed) {

		boolean wasPlaying = false;
		if (soundEffectPlayed.getSoundId() == SoundEffectIds.MORTAR_AND_PESTLE){
			soundEffectPlayed.consume();
			playSound = true;
			soundEngine.playClip(Sound.MORTAR_PESTLE_BONK);

			}
		}

	@Subscribe
	public void onGameTick(GameTick gameTick){
//		if(playSound){
//			soundEngine.playClip(Sound.MORTAR_PESTLE_BONK);
//			playSound = false;
//			}
		}


	@Provides
	MortarPestlePluginConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MortarPestlePluginConfig.class);
	}
}
