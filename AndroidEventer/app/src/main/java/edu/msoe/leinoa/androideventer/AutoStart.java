package edu.msoe.leinoa.androideventer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.msoe.leinoa.androideventer.networking.ServerCommunicatorService;

/**
 * Created by aleino on 11/14/16.
 */

public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ServerCommunicatorService.class));
    }
}
