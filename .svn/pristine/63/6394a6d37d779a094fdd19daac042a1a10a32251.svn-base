package com.tdh.common.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdh.common.R;


public class DayArrayAdapter extends AbstractWheelTextAdapter {
	private int startIndex = -60;
	private int endIndex = 0;

	private Date[] days;

	// Calendar
	Calendar calendar;

	private SimpleDateFormat format = new SimpleDateFormat("yy年M月d日", Locale.CHINA);

	/**
	 * Constructor
	 */
	public DayArrayAdapter(Context context, Calendar calendar) {
		super(context, R.layout.wheel_date, NO_RESOURCE);
		this.calendar = calendar;

		setItemTextResource(R.id.monthday);

		days = new Date[getItemsCount()];
	}

	/**
	 *
	 * @param context
	 * @param startIndex
	 *            开始日期相对于今天的日期天数，在今天之前为负数，否则为正数。假如为昨天，那么startIndex=-1，今天则
	 *            startIndex=0
	 * @param endIndex
	 *            结束日期相对于今天的日期天数，endIndex必须大于startIndex
	 */
	public DayArrayAdapter(Context context, int startIndex, int endIndex) {
		super(context, R.layout.wheel_date, NO_RESOURCE);

		if (endIndex < startIndex) {
			throw new IllegalArgumentException("结束日期必须晚于开始日期");
		}

		this.startIndex = startIndex;
		this.endIndex = endIndex;

		this.calendar = Calendar.getInstance(Locale.CHINA);

		setItemTextResource(R.id.monthday);

		days = new Date[getItemsCount()];
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		int day = index + startIndex;

		Calendar newCalendar = (Calendar) calendar.clone();
		newCalendar.add(Calendar.DATE, day);

		View view = super.getItem(index, cachedView, parent);
		TextView weekday = (TextView) view.findViewById(R.id.weekday);

		// 某些手机设置问题 格式化出来显示不一致, 显示周几自己处理
		int dayOfWeek = newCalendar.get(Calendar.DAY_OF_WEEK);

		weekday.setText(getWeekDayName(dayOfWeek));

		TextView monthday = (TextView) view.findViewById(R.id.monthday);
		if (day == 0) {
			monthday.setText("今天");
			monthday.setTextColor(0xFF0000F0);
		} else {
			monthday.setText(format.format(newCalendar.getTime()));
			monthday.setTextColor(0xFF111111);
		}

		days[index] = newCalendar.getTime();

		return view;
	}

	private CharSequence getWeekDayName(int dayOfWeek) {
		switch (dayOfWeek) {
			case Calendar.SUNDAY:
				return "周日";
			case Calendar.MONDAY:
				return "周一";
			case Calendar.TUESDAY:
				return "周二";
			case Calendar.WEDNESDAY:
				return "周三";
			case Calendar.THURSDAY:
				return "周四";
			case Calendar.FRIDAY:
				return "周五";
			case Calendar.SATURDAY:
				return "周六";
			default:
				break;
		}
		return "";
	}

	@Override
	public int getItemsCount() {
		return endIndex - startIndex + 1;
	}

	public Date getCurrentDay(int index) {
		return days[index];
	}

	@Override
	protected CharSequence getItemText(int index) {
		return "";
	}

}
