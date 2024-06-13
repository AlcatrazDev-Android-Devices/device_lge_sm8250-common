/*
 * Copyright (C) 2024 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lge.aisound;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemProperties;

public class AISound {
    public static final String PROPERTY_AISOUND_SWITCH = "persist.lge.audio.aisound.enable";
    public static final String PROPERTY_AISOUND_MODE = "persist.lge.audio.aisound.mode";

    public static void switchAISound(Context context, boolean enable) {
        AudioManager am = context.getSystemService(AudioManager.class);
        final String paramStr = enable ? "on" : "off";
        am.setParameters("op_virt_on=" + paramStr);
        SystemProperties.set(PROPERTY_AISOUND_SWITCH, paramStr);
    }

    public static void setAISoundMode(Context context, int mode) {
        AudioManager am = context.getSystemService(AudioManager.class);
        am.setParameters("op_virt_mode=" + mode);
        SystemProperties.set(PROPERTY_AISOUND_MODE, mode + "");
    }

    public static boolean getAISoundState(Context context) {
        return SystemProperties.get(PROPERTY_AISOUND_SWITCH, "off").equals("on");
    }

    public static int getAISoundMode(Context context) {
        return SystemProperties.getInt(PROPERTY_AISOUND_MODE, 0);
    }

    public static void onBoot(Context context) {
        switchAISound(context, getAISoundState(context));
        setAISoundMode(context, getAISoundMode(context));
    }
}