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

package com.kogitune.devinfonotification.apps;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

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
