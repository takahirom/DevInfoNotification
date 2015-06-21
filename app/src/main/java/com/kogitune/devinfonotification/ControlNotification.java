package com.kogitune.devinfonotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.kogitune.devinfonotification.apps.AppPackageInfo;

/**
 * TODO: STUB implementation!!
 */
public class ControlNotification {
    private final Context context;
    private final NotificationManager notificationManager;

    private static final String SHOW_NOTIFICATION = "SHOW_CONTROL_NOTIFICATION";
    public static final String KEY_DEBUG_PACKAGE_NAME = "KEY_DEBUG_PACKAGE_NAME";
    private static final int NOTIFICATION_ID = 2;
    private final SharedPreferences preferences;
    private AppPackageInfo debugAppPackageInfo;

    public ControlNotification(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final String debugAppPackageName = preferences.getString(KEY_DEBUG_PACKAGE_NAME, null);
        debugAppPackageInfo = AppPackageInfo.createPackageInfo(context.getPackageManager(), debugAppPackageName);
    }

    public boolean isNotificationEnabled() {
        return preferences.getBoolean(ControlNotification.SHOW_NOTIFICATION, false);
    }
    public boolean setNotificationEnabled(boolean enabled) {
        return preferences.edit().putBoolean(ControlNotification.SHOW_NOTIFICATION, enabled).commit();
    }

    public void settingByPref () {
        if (isNotificationEnabled()) {
            show();
        } else {
            cancel();
        }
    }

    public void show() {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.control_notification);
        if (Build.VERSION_CODES.FROYO < Build.VERSION.SDK_INT) {
            contentView.setOnClickPendingIntent(R.id.detail_button, createDetailPendingIntent());
            contentView.setOnClickPendingIntent(R.id.uninstall_button, createUninstallPendingIntent());
        } else {
            contentView.setViewVisibility(R.id.detail_button, View.GONE);
            contentView.setViewVisibility(R.id.uninstall_button, View.GONE);
        }

        contentView.setOnClickPendingIntent(R.id.time_button, createTimePendingIntent());
        contentView.setOnClickPendingIntent(R.id.locale_button, createLocalePendingIntent());


        Notification notification = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher).setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context,MainActivity.class), 0)).build();
        notification.contentView = contentView;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private PendingIntent createDetailPendingIntent(){
        if (debugAppPackageInfo==null) {
            return null;
        }
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + debugAppPackageInfo.packageName));
        return PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent createUninstallPendingIntent(){
        if (debugAppPackageInfo==null) {
            return null;
        }
        Uri uri = Uri.fromParts("package", debugAppPackageInfo.packageName, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        return PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent createTimePendingIntent() {
        final Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        return PendingIntent.getActivity(context, 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent createLocalePendingIntent() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
        return PendingIntent.getActivity(context, 103, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void cancel() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public String getDebugAppName() {
        if (debugAppPackageInfo == null) {
            return null;
        }
        return debugAppPackageInfo.appName;
    }

    public void setDebugPackageInfo(AppPackageInfo packageInfo) {
        preferences.edit().putString(KEY_DEBUG_PACKAGE_NAME, packageInfo.packageName).commit();
        debugAppPackageInfo = packageInfo;
    }
}
