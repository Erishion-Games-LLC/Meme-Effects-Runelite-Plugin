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
package com.erishiongames.memeeffects.sounds;

import com.erishiongames.memeeffects.AnimationIds;
import com.erishiongames.memeeffects.MemeEffectsPluginConfig;
import javax.inject.Inject;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.eventbus.Subscribe;

public class SoundManager
{
	private int currentAnimationID = 0;

	@Inject
	private SoundEngine soundEngine;

	@Inject
	private MemeEffectsPluginConfig config;

	@Subscribe
	public void onAnimationChanged(AnimationChanged animationChanged){
		currentAnimationID = animationChanged.getActor().getAnimation();
		if(config.enableMortarPestleBonk()){
			tryPlayMortarPestleBonk(currentAnimationID);
		}
	}

	public void tryPlayMortarPestleBonk(int currentAnimationID){
		if(currentAnimationID == AnimationIds.MORTAR_AND_PESTLE.getAnimationID()){
			soundEngine.playClip(SoundFiles.MORTAR_PESTLE_BONK);
		}
	}

	public void tryRemoveMortarPestleSound(SoundEffectPlayed soundEffectPlayed) {
		if (soundEffectPlayed.getSoundId() == SoundEffectIds.MORTAR_AND_PESTLE.getSoundEffectID() && currentAnimationID == AnimationIds.MORTAR_AND_PESTLE.getAnimationID()) {
			soundEffectPlayed.consume();
		}
	}

	@Subscribe
	public void onSoundEffectPlayed(SoundEffectPlayed soundEffectPlayed)
	{
		if(config.enableMortarPestleBonk()){
			tryRemoveMortarPestleSound(soundEffectPlayed);
		}
	}
}
