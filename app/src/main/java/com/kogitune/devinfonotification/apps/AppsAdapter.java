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
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kogitune.devinfonotification.R;

import java.util.ArrayList;
import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ContactViewHolder> {

    private final PackageManager packageManager;
    private final Resources resources;
    private final int imageSize;
    private final Handler handler;
    ArrayList<AppPackageInfo> appPackageInfoList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public AppsAdapter(Resources resources, PackageManager packageManager) {
        this.packageManager = packageManager;
        this.resources = resources;
        imageSize = resources.getDimensionPixelSize(R.dimen.size_big);
        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                appPackageInfoList = getInstalledApps(true);
                notifyDataSetChangedOnUiThread();
            }
        }).start();
    }


    private void notifyDataSetChangedOnUiThread() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appPackageInfoList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int pos) {
        final AppPackageInfo info = appPackageInfoList.get(pos);
        holder.appInfoText.setText(info.appName + " " + info.packageName);

        info.icon.setBounds(0, 0, imageSize, imageSize);
        holder.appInfoText.setCompoundDrawables(info.icon, null, null, null);

        holder.appInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(info);
            }
        });
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.apps_row, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    private ArrayList<AppPackageInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<AppPackageInfo> appPackageInfoList = new ArrayList<AppPackageInfo>();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo p = packageInfoList.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            final AppPackageInfo appPackageInfo = AppPackageInfo.parsePackageInfo(packageManager, p);
            appPackageInfoList.add(appPackageInfo);
        }
        return appPackageInfoList;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView appInfoText;

        public ContactViewHolder(View v) {
            super(v);
            appInfoText = (TextView) v.findViewById(R.id.appname);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(AppPackageInfo appPackageInfo);
    }
}
