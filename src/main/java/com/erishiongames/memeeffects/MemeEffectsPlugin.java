/* BSD 2-Clause License
 * Copyright (c) 2023, Erishion Games LLC <https://github.com/Erishion-Games-LLC>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.erishiongames.memeeffects;

import com.erishiongames.memeeffects.sounds.SoundFileManager;
import com.erishiongames.memeeffects.sounds.SoundManager;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.OkHttpClient;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@PluginDescriptor(
	name = "Meme Effects"
)
public class MemeEffectsPlugin extends Plugin
{
	public static final String CONFIG_GROUP = "memeeffects";

	@Inject
	private Client client;

	@Inject
	private MemeEffectsPluginConfig config;

	@Inject
	private ScheduledExecutorService executor;

	@Inject
	private OkHttpClient okHttpClient;

	@Inject
	private SoundManager soundManager;

	@Inject
	private EventBus eventBus;


	@Override
	protected void startUp() throws Exception
	{
		eventBus.register(soundManager);

		executor.submit(() -> {
			SoundFileManager.ensureDownloadDirectoryExists();
			SoundFileManager.downloadAllMissingSounds(okHttpClient);
		});
	}

	@Override
	protected void shutDown() throws Exception
	{
		eventBus.unregister(soundManager);
	}

	@Subscribe
	public void onOverheadTextChanged(OverheadTextChanged textChanged)
	{
		if(textChanged.getOverheadText().equals("Smashing!"))
		{
			textChanged.getActor().setOverheadText("Rock and Stone!");
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if(chatMessage.getMessage().equals("Smashing!"))
		{
			MessageNode messageNode = chatMessage.getMessageNode();
			messageNode.setValue("Rock and Stone");

			chatMessage.setMessageNode(messageNode);
		}
	}



	@Provides
	MemeEffectsPluginConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MemeEffectsPluginConfig.class);
	}
}
