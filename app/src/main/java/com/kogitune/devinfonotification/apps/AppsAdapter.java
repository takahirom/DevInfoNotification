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
    private final PackageManager mPackageManager;
    ArrayList<PInfo> mAppn;

    public AppsAdapter(PackageManager packageManager) {
        this.mPackageManager = packageManager;
        mAppn = getInstalledApps(true);
    }

    @Override
    public int getItemCount() {

        return mAppn.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int pos) {
        holder.mAppname.setText(mAppn.get(pos).mPname);
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
        List<PackageInfo> packs = mPackageManager.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.mAppname = p.applicationInfo.loadLabel(mPackageManager).toString();
            newInfo.mPname = p.packageName;
            newInfo.mVersionName = p.versionName;
            newInfo.mVersionCode = p.versionCode;
            newInfo.mIcon = p.applicationInfo.loadIcon(mPackageManager);
            res.add(newInfo);
        }
        return res;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView mAppname;

        public ContactViewHolder(View v) {
            super(v);
            mAppname = (TextView) v.findViewById(R.id.appname);
        }
    }
}
