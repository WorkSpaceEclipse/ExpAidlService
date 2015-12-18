package com.example.expaidlservice;

import com.example.expservice.TestService;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		it = new Intent();
		it.setComponent(new ComponentName("com.example.expservice",
				"com.example.expservice.ExpService"));
		initViews();
	}

	private void initViews() {
		Button btStart = (Button) findViewById(R.id.button1);
		Button btStop = (Button) findViewById(R.id.button2);
		Button btBind = (Button) findViewById(R.id.button3);
		Button btUnBind = (Button) findViewById(R.id.button4);
		btStart.setOnClickListener(this);
		btStop.setOnClickListener(this);
		btBind.setOnClickListener(this);
		btUnBind.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			startService(it);
			break;
		case R.id.button2:
			stopService(it);
			break;
		case R.id.button3:
			bindService(new Intent("ExpServiceStart"), connection,
					Service.BIND_AUTO_CREATE);
			break;
		case R.id.button4:
			try {
				unbindService(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		}
	}

	private ServiceConnection connection = new ServiceConnection() {

		private TestService testService;

		@Override
		public void onServiceDisconnected(ComponentName name) {
			try {
				testService.stopService("1111");
				testService = null;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			testService = TestService.Stub.asInterface(service);
			try {
				testService.startService("0000");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
}
