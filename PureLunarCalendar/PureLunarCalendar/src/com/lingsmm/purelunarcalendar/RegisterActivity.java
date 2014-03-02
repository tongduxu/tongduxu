package com.lingsmm.purelunarcalendar;

import com.lingsmm.purelunarcalendar.ui.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	private EditText accountName;
	private EditText passWord;
	private Button ok;
	private Button reset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		accountName = (EditText) super.findViewById(R.id.register_edit_account);
		passWord = (EditText) super.findViewById(R.id.register_edit_pwd);
		ok = (Button) super.findViewById(R.id.register_btn_ok);
		reset = (Button) super.findViewById(R.id.register_btn_reset);
		ok.setOnClickListener(new OkOnClickListener());
		reset.setOnClickListener(new ResetOnClickListener());
	}

	private class OkOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String strAccountName = accountName.getText().toString();
			String strPassWord = passWord.getText().toString();
			SharedPreferences preferences = getSharedPreferences("account",
					RegisterActivity.MODE_WORLD_READABLE);
			Editor edit = preferences.edit();
			edit.putString("accountName", strAccountName);
			edit.putString("passWord", strPassWord);
			edit.commit();
			Intent intent  = new Intent();
			intent.setClass(RegisterActivity.this, LoginActivity.class);
			RegisterActivity.this.startActivity(intent);
		}

	}
	
	private class ResetOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			accountName.setText("");
			passWord.setText("");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
