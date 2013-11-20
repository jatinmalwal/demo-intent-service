package com.jmintentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentService extends IntentService {

	public MyIntentService() {
		super("MY_SERVICE");
		Log.d(Constants.TAG, "MyIntentService Constructor");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(Constants.TAG, "MyIntentService onHandleIntent");
		Log.d(Constants.TAG, intent.getStringExtra("name"));
		
		

		for (int j = 0; j < 10; j++) {
			try {
				Log.d(Constants.TAG, "Service Running : " + j);
				Thread.sleep(500L);
//				Log.d(Constants.TAG, "STAMP : " + System.currentTimeMillis());

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		String status  = "DONE";
		/*
		 * Creates a new Intent containing a Uri object BROADCAST_ACTION is a
		 * custom Intent action
		 */
		Intent localIntent = new Intent(Constants.BROADCAST_ACTION_ONE)
		// Puts the status into the Intent
				.putExtra(Constants.EXTENDED_DATA_STATUS,status);
		// Broadcasts the Intent to receivers in this app.
		LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
		
		
		Intent localIntent1 = new Intent(Constants.BROADCAST_ACTION_TWO)
		// Puts the status into the Intent
				.putExtra(Constants.EXTENDED_DATA_STATUS,status);
		// Broadcasts the Intent to receivers in this app.
		LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent1);
		
	}
	
	
	

	@Override
	public void onDestroy() {
		Log.d(Constants.TAG, "MyIntentService onDestroy");
		super.onDestroy();
	}

}
