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

import com.erishiongames.memeeffects.messages.MessageValues;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(MemeEffectsPlugin.CONFIG_GROUP)
public interface MemeEffectsPluginConfig extends Config
{
	@ConfigSection
	(
		name = "Audio Changer",
		description = "Audio Configuration Values",
		position = 1
	)
		String audioChanger = "Audio Changer";

		@ConfigItem
		(
			keyName = "Volume",
			name = "Volume",
			description = "Adjust how loud the sound effects are",
			position = 0,
			section = audioChanger
		)
			default int Volume() {return 100;}

		@ConfigItem
		(
			keyName = "enableMortarPestleBonk",
			name = "Enable mortar and pestle bonk",
			description = "Enable mortar and pestle bonking sound",
			position = 1,
			section = audioChanger
		)
		default boolean enableMortarPestleBonk() {return true;}



	@ConfigSection
	(
		name = "Text Changer",
		description = "Text Configuration Values",
		position = 2
	)
		String textChanger = "Text Changer";


		//DPick
		@ConfigItem
		(
			keyName = "enableDragonPick",
			name = "Change Dragon Pickaxe message",
			description = "",
			position = 0,
			section = textChanger
		)
		default boolean enableDragonPick() {return true;}
		@ConfigItem
		(
			keyName = "dPickMessage",
			name = "Dragon Pickaxe Message",
			description = "",
			position = 1,
			section = textChanger
		)
		default String dPickMessage() {return MessageValues.DRAGON_PICKAXE.getMessage();}

		//DAxe
		@ConfigItem
			(
				keyName = "enableDragonAxe",
				name = "Change Dragon Axe message",
				description = "",
				position = 2,
				section = textChanger
			)
		default boolean enableDragonAxe() {return true;}
		@ConfigItem
			(
				keyName = "dAxeMessage",
				name = "Dragon Axe Message",
				description = "",
				position = 3,
				section = textChanger
			)
		default String dAxeMessage() {return MessageValues.DRAGON_AXE.getMessage();}


		//DHarpoon
		@ConfigItem
			(
				keyName = "enableDragonHarpoon",
				name = "Change Dragon Harpoon message",
				description = "",
				position = 4,
				section = textChanger
			)
		default boolean enableDragonHarpoon() {return true;}
		@ConfigItem
			(
				keyName = "dHarpoonMessage",
				name = "Dragon Harpoon Message",
				description = "",
				position = 5,
				section = textChanger
			)
		default String dHarpoonMessage() {return MessageValues.DRAGON_HARPOON.getMessage();}

		//DBattleaxe
		@ConfigItem
			(
				keyName = "enableDragonBattleaxe",
				name = "Change Dragon Battleaxe message",
				description = "",
				position = 6,
				section = textChanger
			)
		default boolean enableDragonBattleaxe() {return true;}
		@ConfigItem
			(
				keyName = "dBattleaxeMessage",
				name = "Dragon Battleaxe message",
				description = "",
				position = 7,
				section = textChanger
			)
		default String dBattleaxeMessage() {return MessageValues.DRAGON_BATTLEAXE.getMessage();}

		//Excalibur
		@ConfigItem
			(
				keyName = "enableExcalibur",
				name = "Change Excalibur message",
				description = "",
				position = 8,
				section = textChanger
			)
		default boolean enableExcalibur() {return true;}
		@ConfigItem
			(
				keyName = "excaliburMessage",
				name = "Excalibur Message",
				description = "",
				position = 9,
				section = textChanger
			)
		default String excaliburMessage() {return MessageValues.EXCALIBUR.getMessage();}

//		//Custom Changer
//		@ConfigItem
//			(
//				keyName = "enableCustomChanger",
//				name = "Change Custom message",
//				description = "",
//				position = 10,
//				section = textChanger
//			)
//		default boolean enableCustomChanger() {return true;}
//		@ConfigItem
//			(
//				keyName = "messageToReplace",
//				name = "Message To Replace",
//				description = "",
//				position = 11,
//				section = textChanger
//			)
//		default String messageToReplace() {return "Set message to replace here";}
//		@ConfigItem
//			(
//				keyName = "customMessageReplacement",
//				name = "Custom Message Replacement",
//				description = "",
//				position = 12,
//				section = textChanger
//			)
//		default String customMessageReplacement() {return "Set the custom message replacement here";}
}