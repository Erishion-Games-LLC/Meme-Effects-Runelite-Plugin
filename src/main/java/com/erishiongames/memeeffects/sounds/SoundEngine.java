/* BSD 2-Clause License
 * Copyright (c) 2021, m0bilebtw
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

import com.erishiongames.memeeffects.MemeEffectsPluginConfig;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;


@Singleton
@Slf4j
public class SoundEngine {

    //Copied from https://github.com/m0bilebtw/c-engineer-completed/blob/master/src/main/java/com/github/m0bilebtw/SoundEngine.java

    @Inject
    private MemeEffectsPluginConfig config;

    private static final long CLIP_MTIME_UNLOADED = -2;
    private long lastClipMTime = CLIP_MTIME_UNLOADED;
    private Clip clip = null;


    private boolean loadClip(SoundFiles sound) {
        try (InputStream stream = new BufferedInputStream(SoundFileManager.getSoundStream(sound))) {
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(stream)) {
                clip.open(audioInputStream); // liable to error with pulseaudio, works on windows, one user informs me mac works
            }
            return true;
        }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {
            }
            return false;
        }

    public void playClip(SoundFiles sound) {
        long currentMTime = System.currentTimeMillis();
        if (clip == null || currentMTime != lastClipMTime || !clip.isOpen()) {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }

            try { clip = AudioSystem.getClip();
            }
            catch (LineUnavailableException e) {
                lastClipMTime = CLIP_MTIME_UNLOADED;
                return;
            }

            lastClipMTime = currentMTime;
            if (!loadClip(sound)) {
                return;
            }
        }

        // User configurable volume
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = 20f * (float) Math.log10(config.Volume() / 100f);
        gain = Math.min(gain, volume.getMaximum());
        gain = Math.max(gain, volume.getMinimum());
        volume.setValue(gain);

        // From RuneLite base client Notifier class:
        // Using loop instead of start + setFramePosition prevents the clip
        // from not being played sometimes, presumably a race condition in the
        // underlying line driver
        clip.loop(0);
    }
}
