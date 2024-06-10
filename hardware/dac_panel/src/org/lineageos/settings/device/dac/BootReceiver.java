package org.lineageos.settings.device.dac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Alc", "rcv 1");
        if (context == null) {
            return;
        }
        Log.e("Alc", "rcv 2");
        context.startService(new Intent(context, QuadDACPlaybackListenerService.class));
    }
}
