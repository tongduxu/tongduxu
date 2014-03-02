package com.lingsmm.purelunarcalendar.ui.activity;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lingsmm.purelunarcalendar.R;

public class EditSpinner extends LinearLayout {

	private ListView listView;
	private String datas[] = { "��Ϸ���Ϯ", "���Ӿ�����", "Ů�������鲻��", "��ͬѧ����" };
	private EditText editText;
	private ImageView imageView;
	private PopupWindow popupWindow;

	public EditSpinner(Context context) {
		this(context, null);
	}

	public EditSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.edit_spinner_layout,
				this, true);
		editText = (EditText) super.findViewById(R.id.editText);
		listView = new ListView(context);
		this.listView.setAdapter(new ArrayAdapter<String>(context,
		android.R.layout.simple_list_item_1, this.datas));
		listView.setOnItemClickListener(new OnItemClickListenerImpl());
		imageView = (ImageView) super.findViewById(R.id.listReason);
		//LayoutInflater inflater = LayoutInflater.from(context);
		//popView = inflater.inflate(R.layout.popupwindow_item, null);
		popupWindow = new PopupWindow(listView, 250,
				LayoutParams.MATCH_PARENT, true);
		// ���õ��������ߴ�����ʧ
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		popupWindow.setFocusable(true);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) { 
				if (popupWindow.isShowing()) { // ���ش��ڣ���������˵��������Сʱ������Ҫ�˷�ʽ����
					popupWindow.dismiss();
				} else { // ��ʾ����
					popupWindow.showAsDropDown(editText, 0, 0);
				}
			}

		});

	}
	
	public String getReason() {
		return editText.getText().toString();
	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String chooseReason = datas[position];
			editText.setText(chooseReason);
			popupWindow.dismiss();
		}
		
	}

}
