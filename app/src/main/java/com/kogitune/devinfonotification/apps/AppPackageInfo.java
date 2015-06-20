package com.kogitune.devinfonotification.apps;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * Created by takam on 2015/06/11.
 */
public class AppPackageInfo {
    public String appName = "";
    public String packageName = "";
    public String versionName = "";
    public int versionCode = 0;
    public Drawable icon;

    public static AppPackageInfo parsePackageInfo(PackageManager packageManager, PackageInfo info) {
        AppPackageInfo newAppInfo = new AppPackageInfo();
        newAppInfo.appName = info.applicationInfo.loadLabel(packageManager).toString();
        newAppInfo.packageName = info.packageName;
        newAppInfo.versionName = info.versionName;
        newAppInfo.versionCode = info.versionCode;
        newAppInfo.icon = info.applicationInfo.loadIcon(packageManager);
        return newAppInfo;
    }

    public static AppPackageInfo createPackageInfo(PackageManager packageManager,String packageName){
        final PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return parsePackageInfo(packageManager, packageInfo);
    }

}
