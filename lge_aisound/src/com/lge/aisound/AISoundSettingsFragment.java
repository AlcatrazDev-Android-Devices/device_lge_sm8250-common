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

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemProperties;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.preference.ListPreference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;

import com.android.settingslib.widget.MainSwitchPreference;

import com.lge.aisound.R;

public class AISoundSettingsFragment extends PreferenceFragment implements
        OnPreferenceChangeListener, CompoundButton.OnCheckedChangeListener {

    public static final String PREF_ENABLE = "aisound_enable";
    public static final String PREF_PROFILE = "aisound_profile";

    private MainSwitchPreference mSwitchBar;
    private ListPreference mProfilePref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.aisound_settings);
        boolean enable = AISound.getAISoundState(getContext());

        mSwitchBar = (MainSwitchPreference) findPreference(PREF_ENABLE);
        mSwitchBar.addOnSwitchChangeListener(this);
        mSwitchBar.setChecked(enable);

        mProfilePref = (ListPreference) findPreference(PREF_PROFILE);
        mProfilePref.setOnPreferenceChangeListener(this);
        mProfilePref.setEnabled(enable);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case PREF_PROFILE:
                AISound.setAISoundMode(getContext(), Integer.parseInt((newValue.toString())));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mSwitchBar.setChecked(isChecked);
        mProfilePref.setEnabled(isChecked);
        AISound.switchAISound(getContext(), isChecked);
    }
}
