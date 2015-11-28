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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kogitune.devinfonotification.apps.AppPackageInfo;
import com.kogitune.devinfonotification.apps.AppsAdapter;
import com.kogitune.devinfonotification.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private DevInfoNotification devInfoNotification;
    private TextView debugAppText;
    private ControlNotification controlNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setSdkVersion(Build.VERSION.SDK_INT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        devInfoNotification = new DevInfoNotification(this, new HardwareInfo());
        devInfoNotification.settingByPref();

        controlNotification = new ControlNotification(this);
        controlNotification.settingByPref();
        setupViews();
    }

    private void setupViews() {
        debugAppText = ((TextView) findViewById(R.id.debug_app));
        if (controlNotification.getDebugAppName() != null) {
            debugAppText.setText(controlNotification.getDebugAppName());
        }

        findViewById(R.id.go_store).setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        findViewById(R.id.go_github).setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/takahirom/DevInfoNotification")));
            }

        });
        boolean isNotificationShow = devInfoNotification.isNotificationEnabled();

        SwitchCompat showSwitchCompat = (SwitchCompat) findViewById(R.id.is_show_notification);
        showSwitchCompat.setChecked(isNotificationShow);
        showSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                devInfoNotification.setNotificationEnabled(isChecked);
                if (isChecked) {
                    devInfoNotification.show();
                } else {
                    devInfoNotification.cancel();
                }
            }
        });

        findViewById(R.id.select_debug_app).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectAppDialog();
            }
        });

        boolean isControlNotificationShow = controlNotification.isNotificationEnabled();
        SwitchCompat showControlSwitchCompat = (SwitchCompat) findViewById(R.id.is_show_controll_notification);
        showControlSwitchCompat.setChecked(isControlNotificationShow);
        showControlSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                controlNotification.setNotificationEnabled(isChecked);
                if (isChecked) {
                    controlNotification.show();
                } else {
                    controlNotification.cancel();
                }
            }
        });

    }

    private void showSelectAppDialog() {
        final RecyclerView recyclerView = new RecyclerView(MainActivity.this);
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        final AppsAdapter adapter = new AppsAdapter(getResources(), getPackageManager());
        adapter.setOnItemClickListener(new AppsAdapter.OnItemClickListener() {

            @Override
            public void onItemClicked(AppPackageInfo appPackageInfo) {
                controlNotification.setDebugPackageInfo(appPackageInfo);
                final String debugAppName = controlNotification.getDebugAppName();
                if (debugAppName != null) {
                    debugAppText.setText(debugAppName);
                }
                controlNotification.settingByPref();
                alertDialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        alertDialog.setView(recyclerView);
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*
        if (id == R.id.action_settings) {
            return true;
        }
		*/
        return super.onOptionsItemSelected(item);
    }

}
