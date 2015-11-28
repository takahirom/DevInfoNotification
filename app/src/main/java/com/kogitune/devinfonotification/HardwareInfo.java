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

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class HardwareInfo {

    public String getModel() {
        return Build.MODEL;
    }

    public String getOs() {
        return Build.VERSION.RELEASE + "(" + Build.VERSION.SDK_INT + ")";

    }

    public String getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        return disp.getWidth() + "x" + disp.getHeight();
    }

    public String getDpi(Context context) {
        String dpi = null;
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                dpi = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                dpi = "MDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                dpi = "HDPI";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                dpi = "XHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                dpi = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                dpi = "XXXHDPI";
                break;
            case DisplayMetrics.DENSITY_TV:
                dpi = "TVDPI";
                break;

        }

        dpi += "(x" + context.getResources().getDisplayMetrics().density + ")";
        return dpi;
    }

}
