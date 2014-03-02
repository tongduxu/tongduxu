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
	private String datas[] = { "游戏瘾来袭", "电视剧诱人", "女朋友心情不好", "陪同学聊天" };
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
		// 设置点击窗口外边窗口消失
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		popupWindow.setFocusable(true);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) { 
				if (popupWindow.isShowing()) { // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
					popupWindow.dismiss();
				} else { // 显示窗口
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
