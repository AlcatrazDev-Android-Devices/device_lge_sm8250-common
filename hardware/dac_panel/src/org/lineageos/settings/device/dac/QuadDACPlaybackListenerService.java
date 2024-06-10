package org.lineageos.settings.device.dac;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.lineageos.settings.device.dac.utils.QuadDAC;

import java.util.List;

import vendor.lge.hardware.audio.dac.control.V1_0.IDacAdvancedControl;
import vendor.lge.hardware.audio.dac.control.V1_0.IDacHalControl;

public class QuadDACPlaybackListenerService extends Service {
    private int mPreviousStartPid = 0;
    private IDacAdvancedControl dac;
    private IDacHalControl dhc;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try {
            dac = IDacAdvancedControl.getService(true);
            dhc = IDacHalControl.getService(true);
        } catch(Exception e) {

        }
        manager.registerAudioPlaybackCallback(new AudioManager.AudioPlaybackCallback() {
            @Override
            public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> configs) {
                super.onPlaybackConfigChanged(configs);
                Log.e("Alc","Callback received, Iteration >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                for (AudioPlaybackConfiguration i : configs) {
                    Log.e("Alc", "iter in, attr="+i.getAudioAttributes().toString());
                    if(i.getAudioAttributes().getUsage() == AudioAttributes.USAGE_MEDIA) {
                        Log.e("Alc", "media");
                        if(i.isActive()) {  // If the player is calling start
                            Log.e("Alc", "active");
                            resetDACStatus();
                        }
                    }
                }
                Log.e("Alc", "Callback received, Iteration <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }
        }, null);
        return super.onStartCommand(intent, flags, startId);
    }

    private void resetDACStatus() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                boolean savedDACState = QuadDAC.isEnabled(dhc);
                Log.e("Alc", "getDAC state = "+savedDACState);
                if(savedDACState) {
                    QuadDAC.enable(dhc, dac);
                } else {
                    QuadDAC.disable(dhc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}