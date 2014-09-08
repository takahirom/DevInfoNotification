package com.kogitune.devinfonotification;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by takam on 2014/09/08.
 */
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
