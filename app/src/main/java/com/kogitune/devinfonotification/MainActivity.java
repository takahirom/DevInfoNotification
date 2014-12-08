package com.kogitune.devinfonotification;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.preference.*;


public class MainActivity extends Activity {
    private static final String SHOW_NOTIFICATION = "SHOW_NOTIFICATION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		Boolean isNotificationShow = preferences.getBoolean(SHOW_NOTIFICATION,true);
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
		if(isNotificationShow){
            new DevInfoNotification(this,new HardwareInfo()).show();
		}
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
