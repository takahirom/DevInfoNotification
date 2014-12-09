package com.kogitune.devinfonotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

/**
 * Created by takam on 2014/09/08.
 */
public class Receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Context appContext = context.getApplicationContext();
        new DevInfoNotification(appContext,new HardwareInfo()).settingByPref(PreferenceManager.getDefaultSharedPreferences(appContext));
    }
}
