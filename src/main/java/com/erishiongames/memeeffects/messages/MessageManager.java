package com.erishiongames.memeeffects.messages;

import com.erishiongames.memeeffects.MemeEffectsPluginConfig;
import javax.inject.Inject;
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
		if(config.enableChangeDPick() && textChanged.getOverheadText().equals(MessageValues.DRAGON_PICKAXE.getMessage()))
			{
				if(config.dPickMessage().equals(""))
				{
					textChanged.getActor().setOverheadText("I made a silly!");
					return;
				}
				textChanged.getActor().setOverheadText(config.dPickMessage());
			}
	}

	@Subscribe(priority = -10.0f)
	public void onChatMessage(ChatMessage chatMessage)
	{
		if(config.enableChangeDPick() && chatMessage.getMessage().equals(MessageValues.DRAGON_PICKAXE.getMessage()))
		{
			MessageNode messageNode = chatMessage.getMessageNode();
			if(config.dPickMessage().equals(""))
			{
				messageNode.setValue("MemeEffects: Strings can't be null. Write something in the box or disable the checkbox. Is this check wasn't here, you would have just crashed.");
				return;
			}
			messageNode.setValue(config.dPickMessage());

			chatMessage.setMessageNode(messageNode);
		}
	}
}
