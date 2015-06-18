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

/**
 * Created by takam on 2015/06/11.
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ContactViewHolder> {

    private final PackageManager packageManager;
    private final Resources resources;
    private final int imageSize;
    private final Handler handler;
    ArrayList<AppsPackageInfo> appsPackageInfoList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public AppsAdapter(Resources resources, PackageManager packageManager) {
        this.packageManager = packageManager;
        this.resources = resources;
        imageSize = resources.getDimensionPixelSize(R.dimen.size_big);
        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                appsPackageInfoList = getInstalledApps(true);
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
        return appsPackageInfoList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int pos) {
        final AppsPackageInfo info = appsPackageInfoList.get(pos);
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

    private ArrayList<AppsPackageInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<AppsPackageInfo> appsPackageInfoList = new ArrayList<AppsPackageInfo>();
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            AppsPackageInfo newInfo = new AppsPackageInfo();
            newInfo.appName = p.applicationInfo.loadLabel(packageManager).toString();
            newInfo.packageName = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(packageManager);
            appsPackageInfoList.add(newInfo);
        }
        return appsPackageInfoList;
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
        void onItemClicked(AppsPackageInfo appsPackageInfo);
    }
}
