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
package com.erishiongames.memeeffects.messages;

import com.erishiongames.memeeffects.MemeEffectsPluginConfig;
import java.util.Objects;
import javax.inject.Inject;
import net.runelite.api.Actor;
import net.runelite.api.MessageNode;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.eventbus.Subscribe;

public class MessageManager
{
	@Inject
	private MemeEffectsPluginConfig config;

	@Subscribe(priority = -10.0f)
	public void onOverheadTextChanged(OverheadTextChanged textChanged)
	{
		MessageValues messageValue = getMessageValue(textChanged.getOverheadText());
		if (messageValue == null)
		{
			return;
		}

		if (!shouldEnableMessage(messageValue))
		{
			return;
		}

		String message = getConfigMessage(messageValue);
		Actor actor = textChanged.getActor();
		if (message == null || message.isEmpty())
		{
			actor.setOverheadText("Meme Effects: Message is empty. TEST");
		}
		else
		{
			actor.setOverheadText(message);
		}
	}

	private MessageValues getMessageValue(String message)
	{
		for (MessageValues messageValue : MessageValues.values())
		{
			if (messageValue.getMessage().equals(message))
			{
				return messageValue;
			}
		}
		return null;
	}

	private boolean shouldEnableMessage(MessageValues messageValue)
	{
		switch (messageValue)
		{
			case EXCALIBUR:
				return config.enableExcalibur();
			case DRAGON_BATTLEAXE:
				return config.enableDragonBattleaxe();
			case DRAGON_AXE:
				return config.enableDragonAxe();
			case DRAGON_HARPOON:
				return config.enableDragonHarpoon();
			case DRAGON_PICKAXE:
				return config.enableDragonPick();
			default:
				return false;
		}
	}

	private String getConfigMessage(MessageValues messageValue)
	{
		switch (messageValue) {
			case EXCALIBUR:
				return config.excaliburMessage();
			case DRAGON_BATTLEAXE:
				return config.dBattleaxeMessage();
			case DRAGON_AXE:
				return config.dAxeMessage();
			case DRAGON_HARPOON:
				return config.dHarpoonMessage();
			case DRAGON_PICKAXE:
				return config.dPickMessage();
			default:
				return null;
		}
	}
}
