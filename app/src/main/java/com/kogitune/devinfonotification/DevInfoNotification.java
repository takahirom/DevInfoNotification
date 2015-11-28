/*
 * Copyright (C) 2015 takahirom
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

package com.kogitune.devinfonotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

public class DevInfoNotification {
    private final Context context;
    private final HardwareInfo hardwareInfo;
    private final NotificationManager notificationManager;

    private static final String SHOW_NOTIFICATION = "SHOW_NOTIFICATION";
    private static final int NOTIFICATION_ID = 1;
    private final SharedPreferences preferences;

    public DevInfoNotification(Context context, HardwareInfo info) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        hardwareInfo = info;
    }

    public boolean isNotificationEnabled(){
        return preferences.getBoolean(DevInfoNotification.SHOW_NOTIFICATION,true);
    }
    public boolean setNotificationEnabled(boolean enabled) {
        return preferences.edit().putBoolean(DevInfoNotification.SHOW_NOTIFICATION, enabled).commit();
    }

    public void settingByPref (){
        if(isNotificationEnabled()){
            show();
		} else {
            cancel();
        }
    }

    public void show() {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        contentView.setTextViewText(R.id.text_model, hardwareInfo.getModel());
        contentView.setTextViewText(R.id.text_dpi, hardwareInfo.getDpi(context));
        contentView.setTextViewText(R.id.text_os, hardwareInfo.getOs());
        contentView.setTextViewText(R.id.text_size, hardwareInfo.getScreenSize(context));
        Notification notification = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher).setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context,MainActivity.class), 0)).build();
        notification.contentView = contentView;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
    public void cancel() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
