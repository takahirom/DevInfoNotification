package com.kogitune.devinfonotification.apps;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
    ArrayList<PInfo> appn;

    public AppsAdapter(PackageManager packageManager) {
        this.packageManager = packageManager;
        appn = getInstalledApps(true);
    }

    @Override
    public int getItemCount() {

        return appn.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int pos) {
        for (int i = 0; i < appn.size(); i++) {
            holder.appname.setText(appn.get(i).pname);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.apps_row, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(packageManager).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(packageManager);
            res.add(newInfo);
        }
        return res;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView appname;

        public ContactViewHolder(View v) {
            super(v);
            appname = (TextView) v.findViewById(R.id.appname);
        }
    }
}
