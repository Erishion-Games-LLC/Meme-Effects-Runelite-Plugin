/* BSD 2-Clause License
 * Copyright (c) 2021, m0bilebtw
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

import com.erishiongames.memeeffects.MemeEffectsPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class SoundFileManager {

    //Copied from https://github.com/m0bilebtw/c-engineer-completed/blob/master/src/main/java/com/github/m0bilebtw/SoundFileManager.java

    private static final File DOWNLOAD_DIR = new File(RuneLite.RUNELITE_DIR.getPath() + File.separator + MemeEffectsPlugin.CONFIG_GROUP);
    private static final String DELETE_WARNING_FILENAME = "EXTRA_FILES_WILL_BE_DELETED_BUT_FOLDERS_WILL_REMAIN";
    private static final File DELETE_WARNING_FILE = new File(DOWNLOAD_DIR, DELETE_WARNING_FILENAME);
    private static final HttpUrl RAW_GITHUB = HttpUrl.parse("https://raw.githubusercontent.com/Erishion-Games-LLC/Meme-Sounds-Runelite-Plugin/master/sounds");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void ensureDownloadDirectoryExists() {
        if (!DOWNLOAD_DIR.exists()) {
            DOWNLOAD_DIR.mkdirs();
        }
        try {
            DELETE_WARNING_FILE.createNewFile();
        } catch (IOException ignored) { }
    }

    public static void downloadAllMissingSounds(final OkHttpClient okHttpClient) {
        File[] downloadDirFiles = DOWNLOAD_DIR.listFiles();

        // Get set of existing files in our dir - existing sounds will be skipped, unexpected files (not dirs) will be deleted
        Set<String> filesPresent = new HashSet<>();
        if (downloadDirFiles != null && downloadDirFiles.length > 0) {
            Arrays.stream(downloadDirFiles)
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .filter(filename -> !DELETE_WARNING_FILENAME.equals(filename))
                    .forEach(filesPresent::add);
        }

        // Download any sounds that are not yet present but exist in SoundFiles enum
        SoundFiles[] allSounds = SoundFiles.values();
        for (SoundFiles sound : allSounds) {
            String fileName = sound.getResourceName();
            if (filesPresent.contains(fileName)) {
                filesPresent.remove(fileName);
                continue;
            }

            if (RAW_GITHUB == null) {
                // Hush intellij, it's okay, the potential NPE can't hurt you now
                log.error("Could not download sounds due to an unexpected null RAW_GITHUB value");
                return;
            }
            HttpUrl soundUrl = RAW_GITHUB.newBuilder().addPathSegment(fileName).build();
            Path outputPath = Paths.get(DOWNLOAD_DIR.getPath(), fileName);
            try (Response res = okHttpClient.newCall(new Request.Builder().url(soundUrl).build()).execute()) {
                if (res.body() != null)
                    Files.copy(new BufferedInputStream(res.body().byteStream()), outputPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error("Could not download sounds", e);
                return;
            }
        }

        // filesPresent now contains only files in our directory that we weren't expecting
        // (eg. old versions of sounds)
        // We now delete them to avoid cluttering up disk space
        // We leave dirs behind (filesPresent filters them out early on) as we aren't creating those anyway so they won't build up over time
        for (String filename : filesPresent) {
            File toDelete = new File(DOWNLOAD_DIR, filename);
            //noinspection ResultOfMethodCallIgnored
            toDelete.delete();
        }
    }

    public static InputStream getSoundStream(SoundFiles sound) throws FileNotFoundException {
        return new FileInputStream(new File(DOWNLOAD_DIR, sound.getResourceName()));
    }
}