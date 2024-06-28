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

import android.media.audiofx.AudioEffect;
import android.util.Log;

import java.util.UUID;

public class AISoundEffect extends AudioEffect {

    private static final String TAG = "LGEAISound";
    private static final UUID EFFECT_TYPE_AISOUND =
            UUID.fromString("e1abd756-97b7-11e9-bc42-526af7764f64");
    private static final int AISOUND_PARAM = 5;

    public AISoundEffect(int priority, int audioSession) {
        super(EFFECT_TYPE_NULL, EFFECT_TYPE_AISOUND, priority, audioSession);
    }

    public void setAISoundOn(boolean on) {
        super.setEnabled(on);
    }
}
