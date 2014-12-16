package com.kogitune.devinfonotification;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v7.widget.SwitchCompat;
import android.view.*;
import android.view.View.*;
import android.preference.*;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;


public class MainActivity extends ActionBarActivity {
    private DevInfoNotification mDevInfoNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

		findViewById(R.id.go_store).setOnClickListener(new OnClickListener(){
			
			public void onClick(View view){
				final String appPackageName = getPackageName();
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
				} catch (ActivityNotFoundException e) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
				}
			}
		});
		findViewById(R.id.go_github).setOnClickListener(new OnClickListener(){

				public void onClick(View view){
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/takahirom/DevInfoNotification")));
				}

			});

        mDevInfoNotification = new DevInfoNotification(this, new HardwareInfo());

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isNotificationShow = preferences.getBoolean(DevInfoNotification.SHOW_NOTIFICATION,true);
        SwitchCompat showSwitchCompat = (SwitchCompat) findViewById(R.id.is_show_notification);
        showSwitchCompat.setChecked(isNotificationShow);
        showSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean(DevInfoNotification.SHOW_NOTIFICATION,isChecked).commit();
				if (isChecked){
                    mDevInfoNotification.show();
                }else{
                    mDevInfoNotification.cancel();
                }
            }
        });

        mDevInfoNotification.settingByPref(preferences);
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
