package com.kogitune.devinfonotification;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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

import com.kogitune.devinfonotification.apps.AppsAdapter;
import com.kogitune.devinfonotification.apps.AppsPackageInfo;


public class MainActivity extends AppCompatActivity {
    private DevInfoNotification devInfoNotification;
    private TextView debugAppText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        devInfoNotification = new DevInfoNotification(this, new HardwareInfo());
        devInfoNotification.settingByPref();
        setupViews();
    }

    private void setupViews() {
        debugAppText = ((TextView) findViewById(R.id.debug_app));
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
            public void onItemClicked(AppsPackageInfo appsPackageInfo) {
                debugAppText.setText(appsPackageInfo.appName);
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
