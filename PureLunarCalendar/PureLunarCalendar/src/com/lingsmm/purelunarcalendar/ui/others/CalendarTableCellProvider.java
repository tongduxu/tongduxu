package com.lingsmm.purelunarcalendar.ui.others;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingsmm.purelunarcalendar.R;
import com.lingsmm.purelunarcalendar.module.DateFormatter;
import com.lingsmm.purelunarcalendar.module.LunarCalendar;

public class CalendarTableCellProvider {

	private long firstDayMillis = 0;
	private int solarTerm1 = 0;
	private int solarTerm2 = 0;
	private DateFormatter fomatter;
	
	public CalendarTableCellProvider(Resources resources, int monthIndex){
		int year = LunarCalendar.getMinYear() + (monthIndex / 12);
		int month = monthIndex % 12;
		Calendar date = new GregorianCalendar(year, month, 1);
		int offset = 1 - date.get(Calendar.DAY_OF_WEEK);
		date.add(Calendar.DAY_OF_MONTH, offset);
		firstDayMillis = date.getTimeInMillis();
		solarTerm1 = LunarCalendar.getSolarTerm(year, month * 2 + 1);
		solarTerm2 = LunarCalendar.getSolarTerm(year, month * 2 + 2);
		fomatter = new DateFormatter(resources);
	}
	
	public View getView(int position, LayoutInflater inflater, ViewGroup container) {
		ViewGroup rootView;
		LunarCalendar date = new LunarCalendar(firstDayMillis + 
				(position - (position / 8) - 1) * LunarCalendar.DAY_MILLIS);
		// �ܵ��������
		if (position % 8 == 0){
			rootView = (ViewGroup) inflater.inflate(R.layout.view_calendar_week_index, container, false);

			TextView txtWeekIndex = (TextView)rootView.findViewById(R.id.txtWeekIndex);
			txtWeekIndex.setText(String.valueOf(date.getGregorianDate(Calendar.WEEK_OF_YEAR)));

			return rootView;
		}

		// ��ʼ���ڴ���
		boolean isFestival = false, isSolarTerm = false;
		rootView = (ViewGroup) inflater.inflate(R.layout.view_calendar_day_cell, container, false);
		TextView txtCellGregorian = (TextView)rootView.findViewById(R.id.txtCellGregorian);
		TextView txtCellLunar = (TextView)rootView.findViewById(R.id.txtCellLunar);
		
		int gregorianDay = date.getGregorianDate(Calendar.DAY_OF_MONTH);
		// �ж��Ƿ�Ϊ��������
		boolean isOutOfRange = ((position % 8 != 0) && 
				(position < 8 && gregorianDay > 7) || (position > 8 && gregorianDay < position - 7 - 6));
		txtCellGregorian.setText(String.valueOf(gregorianDay));

		// ũ������ > �������� > ũ���·� > ��ʮ�Ľ��� > ũ����
		int index = date.getLunarFestival();
		if (index >= 0){
			// ũ������
			txtCellLunar.setText(fomatter.getLunarFestivalName(index));
			isFestival = true;
		}else{
			index = date.getGregorianFestival();
			if (index >= 0){
				// ��������
				txtCellLunar.setText(fomatter.getGregorianFestivalName(index));
				isFestival = true;
			}else if (date.getLunar(LunarCalendar.LUNAR_DAY) == 1){
				// ��һ,��ʾ�·�
				txtCellLunar.setText(fomatter.getMonthName(date));
			}else if(!isOutOfRange && gregorianDay == solarTerm1){
				// ����1
				txtCellLunar.setText(fomatter.getSolarTermName(date.getGregorianDate(Calendar.MONTH) * 2));
				isSolarTerm = true;
			}else if(!isOutOfRange && gregorianDay == solarTerm2){
				// ����2
				txtCellLunar.setText(fomatter.getSolarTermName(date.getGregorianDate(Calendar.MONTH) * 2 + 1));
				isSolarTerm = true;
			}else{
				txtCellLunar.setText(fomatter.getDayName(date));
			}
		}
		
		// set style
		Resources resources = container.getResources();
		if (isOutOfRange){
			rootView.setBackgroundResource(R.drawable.selector_calendar_outrange);
			txtCellGregorian.setTextColor(resources.getColor(R.color.color_calendar_outrange));
			txtCellLunar.setTextColor(resources.getColor(R.color.color_calendar_outrange));
		}else if(isFestival){
			txtCellLunar.setTextColor(resources.getColor(R.color.color_calendar_festival));
		}else if(isSolarTerm){
			txtCellLunar.setTextColor(resources.getColor(R.color.color_calendar_solarterm));
		}
		if (position % 8 == 1 || position % 8 == 7){
			rootView.setBackgroundResource(R.drawable.selector_calendar_weekend);
		}
		if (date.isToday()){
			ImageView imgCellHint = (ImageView) rootView.findViewById(R.id.imgCellHint);
			imgCellHint.setBackgroundResource(R.drawable.img_hint_today);
			rootView.setBackgroundResource(R.drawable.shape_calendar_cell_today);
		}
		
		// store date into tag
		rootView.setTag(date);
		
		return rootView;
	}

}

