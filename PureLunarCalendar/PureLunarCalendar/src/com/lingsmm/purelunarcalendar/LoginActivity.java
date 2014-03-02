package com.lingsmm.purelunarcalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lingsmm.purelunarcalendar.ui.activity.MainActivity;

public class LoginActivity extends Activity {
	
	private Button login;
	private Button register;
	private EditText accountNumber;
	private EditText password;
	private String strAccountNumber;
	private String strPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login = (Button) super.findViewById(R.id.login_btn_login);
		register = (Button) super.findViewById(R.id.login_btn_register);
		accountNumber = (EditText) super.findViewById(R.id.login_edit_account);
		password = (EditText) super.findViewById(R.id.login_edit_pwd);
		login.setOnClickListener(new LoginListener());
		register.setOnClickListener(new RegisterListener());
	}
	
	private class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			strAccountNumber = accountNumber.getText().toString();
			strPassword = password.getText().toString();
			SharedPreferences ferences=getSharedPreferences("account",0);  
            String accountName=ferences.getString("accountName", "");  
            String passWord=ferences.getString("passWord", "");
            if(strAccountNumber.equals(accountName)&&strPassword.equals(passWord)) {
            	Intent intent  = new Intent();
    			intent.setClass(LoginActivity.this, MainActivity.class);
    			LoginActivity.this.startActivity(intent);
            }else {
            	Toast.makeText(LoginActivity.this, "’À∫≈ªÚ√‹¬Î¥ÌŒÛ", Toast.LENGTH_SHORT).show();
            }
		}
		
	}
	
	private class RegisterListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent  = new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			LoginActivity.this.startActivity(intent);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
