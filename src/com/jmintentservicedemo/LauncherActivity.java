package com.jmintentservicedemo;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LauncherActivity extends Activity implements OnClickListener {

	private Intent mServiceIntent;
//	private Handler mHandler;
	// private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private DateFormat dateFormat = SimpleDateFormat.getTimeInstance();
	private Date date;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);

		// Define two intenty filters
		IntentFilter mIntentFilterOne = new IntentFilter(Constants.BROADCAST_ACTION_ONE);
		IntentFilter mIntentFilterTwo = new IntentFilter(Constants.BROADCAST_ACTION_TWO);

		// Instantiates a new Receiver
		MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();

		// Registers the Receiver and its intent filters
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mIntentFilterOne);
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mIntentFilterTwo);

		findViewById(R.id.btn_start).setOnClickListener(this);
		mServiceIntent = new Intent(this, MyIntentService.class);
		mServiceIntent.putExtra("name", "JATIN MALWAL");

		date = new Date(System.currentTimeMillis());
		textView = ((TextView) findViewById(R.id.tv_timer));

		final Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg != null && msg.what == 100) {
					// Log.d(Constants.TAG, "What : " + msg.what);
					changeTime();
				}
				super.handleMessage(msg);
			}
		};

		// This is a timer just for ticking the clock after one second
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// There are two ways to make handler work done one by posting a
				// runnable or by handling the massages in onHandle method.
				mHandler.sendEmptyMessage(100);
				// or

				// mHandler.post(new Runnable() {
				//
				// @Override
				// public void run() {
				// changeTime();
				// }
				// });
			}
		}, 0, 1000);

		Log.d(Constants.TAG, "Launcher Oncreate End");
	}

	private void changeTime() {
		date.setTime(System.currentTimeMillis());
		textView.setText("" + dateFormat.format(date));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// Starts the IntentService
		this.startService(mServiceIntent);

	}

	// Broadcast receiver for receiving status updates from the IntentService
	private class MyBroadcastReceiver extends BroadcastReceiver {

		// Prevents instantiation
		private MyBroadcastReceiver() {
		}

		// Called when the BroadcastReceiver gets an Intent it's registered to
		// receive
		@Override
		public void onReceive(Context context, Intent intent) {
			/*
			 * Handle Intents here.
			 */
			// Log.d(Constants.TAG, "MyBroadcastReceiver onReceive");
			// Toast.makeText(context, "onReceived", Toast.LENGTH_SHORT).show();

			if (intent.getAction() == Constants.BROADCAST_ACTION_ONE) {
				Log.d(Constants.TAG, "ACTION ONE STATUS : " + intent.getStringExtra(Constants.EXTENDED_DATA_STATUS));
			}

			if (intent.getAction() == Constants.BROADCAST_ACTION_TWO) {
				Log.d(Constants.TAG, "ACTION TWO STATUS : " + intent.getStringExtra(Constants.EXTENDED_DATA_STATUS));
			}

			// Legal but NEVER USE
			// Intent intentTemp = new Intent(context, SecondActivity.class);
			// intentTemp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(intentTemp);

		}

	}

}
