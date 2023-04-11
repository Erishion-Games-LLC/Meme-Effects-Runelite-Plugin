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

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(MemeEffectsPlugin.CONFIG_GROUP)
public interface MemeEffectsPluginConfig extends Config
{
	@ConfigSection
	(
		name = "Don't leave text empty",
		description = "Don't leave text empty",
		position = 0
	)
		String dontLeaveEmpty = "Don't leave text empty";

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

		@ConfigItem
		(
			keyName = "enableChangeDPick",
			name = "Change Dragon Pickaxe message",
			description = "Change message of the Dragon Pickaxe from Smashing!",
			position = 0,
			section = textChanger
		)
		default boolean enableChangeDPick() {return true;}

		@ConfigItem
			(
				keyName = "dPickMessage",
				name = "Dragon Pickaxe Message",
				description = "New message of the Dragon Pickaxe special",
				position = 1,
				section = textChanger
			)
		default String dPickMessage() {return "Rock and Stone!";}
}

