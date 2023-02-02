package com.erishiongames.mortarpestle;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.OkHttpClient;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@PluginDescriptor(
	name = "Mortar Pestle"
)
public class MortarPestlePlugin extends Plugin
{
	private int currentAnimationID = 0;

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
		executor.submit(() -> {
			SoundFileManager.ensureDownloadDirectoryExists();
			SoundFileManager.downloadAllMissingSounds(okHttpClient);
		});
	}

	@Override
	protected void shutDown() throws Exception
	{

	}


	//363 is mortal and vial, 364 is mortal and pestle
	@Subscribe
	public void onAnimationChanged(AnimationChanged animationChanged){
		currentAnimationID = animationChanged.getActor().getAnimation();
		log.info(String.valueOf(currentAnimationID));
		if(currentAnimationID == 364){
			soundEngine.playClip(Sound.MORTAR_PESTLE_BONK);
		}
	}

	@Subscribe
	public void onSoundEffectPlayed(SoundEffectPlayed soundEffectPlayed)
	{
		if(soundEffectPlayed.getSoundId() == SoundEffectIds.MORTAR_AND_PESTLE && currentAnimationID == 364){
			soundEffectPlayed.consume();
		}
	}

	@Provides
	MortarPestlePluginConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MortarPestlePluginConfig.class);
	}
}
