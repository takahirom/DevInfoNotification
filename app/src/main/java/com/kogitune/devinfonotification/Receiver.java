package com.kogitune.devinfonotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by takam on 2014/09/08.
 */
public class Receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        new DevInfoNotification(context,new HardwareInfo()).show();
    }
}
