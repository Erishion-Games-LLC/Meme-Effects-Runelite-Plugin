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
			soundEngine.playClip(Sound.MORTAR_PESTLE_BONK);
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
