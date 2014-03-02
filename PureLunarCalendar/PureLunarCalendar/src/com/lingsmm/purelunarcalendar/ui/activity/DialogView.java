package com.lingsmm.purelunarcalendar.ui.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;

import com.lingsmm.purelunarcalendar.R;

public class DialogView extends LinearLayout {

	private TimePicker sleepTime;
	private TimePicker wakeTime;
	private RadioGroup isLateGroup;
	private EditSpinner lateReason;
	private int isLate;

	public DialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.dialog_view_layout, this,
				true);
		sleepTime = (TimePicker) super.findViewById(R.id.sleepTime);
		wakeTime = (TimePicker) super.findViewById(R.id.wakeTime);
		isLateGroup = (RadioGroup) super.findViewById(R.id.RadioGroup01);
		lateReason = (EditSpinner) super.findViewById(R.id.reasonList);
		isLateGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				isLate = checkedId;
			}
		});
	}

	public String getSleepTime() {
		int sleepHour, sleepMinute;
		String strSleepTime;
		sleepHour = sleepTime.getCurrentHour();
		sleepMinute = sleepTime.getCurrentMinute();
		strSleepTime = sleepHour + ":" + sleepMinute;
		return strSleepTime;
	}

	public int getSleepHour() {
		return sleepTime.getCurrentHour();
	}

	public String getWakeTime() {
		int wakeHour, wakeMinute;
		String strWakeTime;
		wakeHour = wakeTime.getCurrentHour();
		wakeMinute = wakeTime.getCurrentMinute();
		strWakeTime = wakeHour + ":" + wakeMinute;
		return strWakeTime;
	}

	public int getWakeHour() {
		return wakeTime.getCurrentHour();
	}

	public int getIsLate() {
		return isLate;
	}

	public String getLateReason() {
		return lateReason.getReason();
	}
}
