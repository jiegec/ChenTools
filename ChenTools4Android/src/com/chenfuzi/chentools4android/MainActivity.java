package com.chenfuzi.chentools4android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chenfuzi.chentoolslib.ChenTools;
import com.chenfuzi.chentoolslib.ChenTools.InputOutput;

public class MainActivity extends Activity implements InputOutput {

	private Spinner spinner;
	private static TextView console;
	private ArrayAdapter<String> adapter;
	private static EditText entertext;
	private static Button enter;
	private boolean syncLock;
	private int currentSelect;
	private TextView versiontext;
	
	private static MainActivity inst;

	private static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				enter.setEnabled(false);
				break;
			case 1:
				enter.setEnabled(true);
				break;
			case 2:
				String currentString = console.getText().toString();
				currentString += (String) msg.obj.toString();
				console.setText(currentString);
				break;
			case 3:
				entertext.setText("");
				break;
			case 4:
				entertext.setInputType((Integer) msg.obj);
				break;
			case 5:
				String version = (String) msg.obj;
				if (version.equals("")) {
					new AlertDialog.Builder(MainActivity.inst).setTitle("更新")
							.setMessage("无法检测更新！").show();
				}
				if (version.compareTo(ChenTools.version) > 0) {
					new AlertDialog.Builder(MainActivity.inst)
							.setTitle("更新")
							.setMessage("检测到新版本！要打开下载链接吗？")
							.setPositiveButton("跳转",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent viewIntent = new

											Intent(
													"android.intent.action.VIEW",
													Uri.parse("https://github.com/wiadufachen/ChenTools/blob/master/ChenTools4Andorid.apk?raw=true"));
											MainActivity.inst.startActivity(viewIntent);
										}
									}).setNegativeButton("取消", null).show();
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		inst = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spinner = (Spinner) findViewById(R.id.spinner1);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ChenTools.m);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		spinner.setVisibility(View.VISIBLE);
		console = (TextView) findViewById(R.id.consoleview);
		entertext = (EditText) findViewById(R.id.entertext);
		enter = (Button) findViewById(R.id.enter);
		enter.setEnabled(false);
		entertext.setText("");
		console.setText("");
		syncLock = true;
		enter.setOnClickListener(new OnClickListener() {
			public void onClick(View w) {
				MainActivity.inst.unlock();
			}
		});
		ChenTools.io = this;
		versiontext = (TextView) findViewById(R.id.versiontext);
		versiontext.setText("ChenTools v" + ChenTools.version);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String version = ChenTools
						.httpDownload("https://github.com/wiadufachen/ChenTools/blob/master/LATEST?raw=true");
				mHandler.obtainMessage(5,version);
			}
		});
		thread.start();
	}

	public void wantNumber() {
		mHandler.obtainMessage(4, InputType.TYPE_CLASS_NUMBER).sendToTarget();
	}

	public void wantText() {
		mHandler.obtainMessage(4, InputType.TYPE_CLASS_TEXT).sendToTarget();
	}

	public void emptyConsole() {
		console.setText("");
	}

	public synchronized boolean lock() throws InterruptedException {
		while (syncLock == true) {
			MainActivity.inst.wait();
		}
		syncLock = true;
		return true;
	}

	public synchronized void unlock() {
		syncLock = false;
		MainActivity.inst.notifyAll();
	}

	public void writeToConsole(String str) {
		mHandler.obtainMessage(2, str).sendToTarget();
	}

	public String getInput() throws InterruptedException {
		mHandler.obtainMessage(1, "").sendToTarget();
		if (!lock()) {
			return "Interrupt";
		}
		String ret = entertext.getText().toString();
		mHandler.obtainMessage(0, "").sendToTarget();
		mHandler.obtainMessage(3, "").sendToTarget();
		return ret;
	}

	public void inputError() {
		new AlertDialog.Builder(this).setTitle("错误").setMessage("请重新输入！")
				.show();
	}

	public Runnable runnable = new Runnable() {
		@Override
		public void run() {
			ChenTools.work(currentSelect);
		}
	};

	class SpinnerSelectedListener implements OnItemSelectedListener {
		public Thread thread = null;

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentSelect = arg2;
			MainActivity.this.emptyConsole();
			if (thread != null) {
				thread.interrupt();
			}
			thread = new Thread(runnable);
			thread.start();
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
