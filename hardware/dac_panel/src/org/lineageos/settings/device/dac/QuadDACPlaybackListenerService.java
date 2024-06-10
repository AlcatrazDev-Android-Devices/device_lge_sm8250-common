package org.lineageos.settings.device.dac;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.lineageos.settings.device.dac.utils.QuadDAC;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import vendor.lge.hardware.audio.dac.control.V1_0.IDacAdvancedControl;

public class QuadDACPlaybackListenerService extends Service {
    private int mPreviousStartPid = 0;
    private IDacAdvancedControl dac;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try { dac = IDacAdvancedControl.getService(true); } catch(Exception e) {}
        new Thread(){
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec("logcat");
                    InputStream is = process.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while((line = br.readLine()) != null){
                        if(line.contains("enable_snd_device")){
                            if(line.contains("headphones-hifi-dac")) {
                                // System resets headphones-hifi routes, reset DAC mode status as well
                                // As we are using prebuilt audio, this is the best trigger for hardware info
                                resetDACMode();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void resetDACMode() {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                int savedDACMode = QuadDAC.getDACMode(dac);
                QuadDAC.setDACMode(dac, savedDACMode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}