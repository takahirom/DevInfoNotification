package com.kogitune.devinfonotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by takam on 2014/09/08.
 */
public class DevInfoNotification {
    private final Context mContext;
    private final HardwareInfo mHardwareInfo;
    private final NotificationManager mNotificationManager;

    public DevInfoNotification(Context context, HardwareInfo info) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mHardwareInfo = info;
    }

    public void show() {
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        contentView.setTextViewText(R.id.text_model, mHardwareInfo.getModel());
        contentView.setTextViewText(R.id.text_dpi, mHardwareInfo.getDpi(mContext));
        contentView.setTextViewText(R.id.text_os, mHardwareInfo.getOs());
        contentView.setTextViewText(R.id.text_size, mHardwareInfo.getScreenSize(mContext));
        Notification notification = new NotificationCompat.Builder(mContext).setSmallIcon(R.drawable.ic_launcher).setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, new Intent(), 0)).build();
        notification.contentView = contentView;
        mNotificationManager.notify(1, notification);
    }
}
