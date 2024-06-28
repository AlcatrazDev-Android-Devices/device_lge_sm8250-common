/*
 * Copyright (C) 2018,2020 The LineageOS Project
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
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public final class AISoundUtils {

    private static final String TAG = "AISoundUtils";
    private static final int EFFECT_PRIORITY = 100;

    private static AISoundUtils mInstance;
    private AISoundEffect mAISoundEffect;
    private Context mContext;

    private AISoundUtils(Context context) {
        mContext = context;
        mAISoundEffect = new AISoundEffect(EFFECT_PRIORITY, 0);
    }

    public static synchronized AISoundUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AISoundUtils(context);
        }
        return mInstance;
    }

    public void onBootCompleted() {
        Log.i(TAG, "onBootCompleted");
        //Todo
    }

    private void checkEffect() {
        if (!mAISoundEffect.hasControl()) {
            Log.w(TAG, "lost control, recreating effect");
            mAISoundEffect.release();
            mAISoundEffect = new AISoundEffect(EFFECT_PRIORITY, 0);
        }
    }

    public void setAISoundOn(boolean on) {
        checkEffect();
        Log.i(TAG, "setAISoundOn: " + on);
        mAISoundEffect.setAISoundOn(on);
    }
}
