package com.lingsmm.purelunarcalendar.ui.activity;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lingsmm.purelunarcalendar.R;
import com.lingsmm.purelunarcalendar.module.DateFormatter;
import com.lingsmm.purelunarcalendar.module.LunarCalendar;
import com.lingsmm.purelunarcalendar.ui.others.CalendarPagerAdapter;

public class MainActivity extends FragmentActivity implements
		OnDateSetListener, OnMenuItemClickListener, OnFocusChangeListener {

	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private View imgPreviousMonth, imgNextMonth;
	private DateFormatter formatter;
	private DialogView dialogView;
	private SleepData sleepData;
	private Cursor mCursor;

	private TextView txtTitleGregorian, txtTitleAddition, txtTitleLunar;

	private int getTodayMonthIndex() {
		Calendar today = Calendar.getInstance();
		int offset = (today.get(Calendar.YEAR) - LunarCalendar.getMinYear())
				* 12 + today.get(Calendar.MONTH);
		return offset;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// hide system title bar
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set to full screen
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		formatter = new DateFormatter(this.getResources());

		imgPreviousMonth = findViewById(R.id.imgPreviousMonth); // 上个月的图片
		imgNextMonth = findViewById(R.id.imgNextMonth); // 下个月的图片
		txtTitleGregorian = (TextView) findViewById(R.id.txtTitleGregorian);
		txtTitleAddition = (TextView) findViewById(R.id.txtTitleAddition);
		txtTitleLunar = (TextView) findViewById(R.id.txtTitleLunar);
		mPager = (ViewPager) findViewById(R.id.pager);

		mPagerAdapter = new CalendarPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new simplePageChangeListener());

		mPager.setCurrentItem(getTodayMonthIndex());
	}

	// 日期单元格点击事件
	public void onCellClick(View v) {
		Toast.makeText(this, v.getTag().toString(), Toast.LENGTH_SHORT).show();
		Log.i("cellClick", v.getTag().toString());
	}

	// 日期对话框选择完成事件
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		int offset = (year - LunarCalendar.getMinYear()) * 12 + monthOfYear;
		mPager.setCurrentItem(offset);
	}

	// 日期单元格焦点变化事件
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus)
			return;
		LunarCalendar lc = (LunarCalendar) v.getTag();
		CharSequence[] info = formatter.getFullDateInfo(lc);
		txtTitleLunar.setText(info[1]);
		txtTitleAddition.setText(info[0]);
		Log.i("cell focus", v.getTag().toString());
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialog_layout, null);
		final DialogView dialogView = (DialogView) layout
				.findViewById(R.id.dialogView);
		final String sleepcalendarDate = formatter.getsolarDate(lc) + "";
		final String sleepTime = dialogView.getSleepTime();
		final int sleepHour = dialogView.getSleepHour();
		final String wakeTime = dialogView.getWakeTime();
		final int wakeHour = dialogView.getWakeHour();
		final int isLate = dialogView.getIsLate();
		new AlertDialog.Builder(this).setTitle("主人的睡眠健康").setView(layout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						final String lateReason = dialogView.getLateReason();
						sleepData = new SleepData(MainActivity.this);
						SQLiteDatabase db = sleepData.getReadableDatabase();
						sleepData.insert(sleepcalendarDate, sleepTime,
								sleepHour, wakeTime, wakeHour, isLate, lateReason);
					}

				}).setNegativeButton("取消", null).show();
	}

	// 标题栏图标点击事件
	public void onMenuImageClick(View v) {
		switch (v.getId()) {
		case R.id.imgPreviousMonth:
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			break;
		case R.id.imgNextMonth:
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			break;
		case R.id.imgToday:
			mPager.setCurrentItem(getTodayMonthIndex());
			break;
		case R.id.imgPopupMenu:
			PopupMenu popup = new PopupMenu(this, v);
			popup.setOnMenuItemClickListener(this);
			popup.inflate(R.menu.activity_main);
			popup.show();
		}
	}

	// 弹出菜单选项点击事件
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuGoto:
			int year = (mPager.getCurrentItem() / 12)
					+ LunarCalendar.getMinYear();
			int month = mPager.getCurrentItem() % 12;
			DatePickerDialog dpd = new DatePickerDialog(
					this,
					android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar,
					this, year, month, 1);
			dpd.getDatePicker().setCalendarViewShown(false);
			dpd.show();
			return true;
		case R.id.menuAbout:
			this.startActivity(new Intent(this, AboutActivity.class));
			return true;
		default:
			return false;
		}
	}

	// 月份显示切换事件
	private class simplePageChangeListener extends
			ViewPager.SimpleOnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			// set title year month
			StringBuilder title = new StringBuilder();
			title.append(LunarCalendar.getMinYear() + (position / 12));
			title.append('-');
			int month = (position % 12) + 1;
			if (month < 10) {
				title.append('0');
			}
			title.append(month);
			txtTitleGregorian.setText(title);
			txtTitleLunar.setText("");
			txtTitleAddition.setText("");

			// set related button's state
			if (position < mPagerAdapter.getCount() - 1
					&& !imgNextMonth.isEnabled()) {
				imgNextMonth.setEnabled(true);
			}
			if (position > 0 && !imgPreviousMonth.isEnabled()) {
				imgPreviousMonth.setEnabled(true);
			}
		}
	}
}
