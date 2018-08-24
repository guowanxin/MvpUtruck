package com.tdh.common.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.tdh.common.R;
import com.tdh.common.adapter.DayArrayAdapter;
import com.tdh.common.adapter.IntermitNumericWheelAdapter;
import com.tdh.common.adapter.NumericWheelAdapter;
import com.tdh.common.utils.CommonUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author：gwx
 * Create at：2017/3/15 14:27
 */
public abstract class DateChoose {
    private Context c;
    private LayoutInflater mInflater;
    private PopupWindow mWndDateSelector;

    public DateChoose(Context c) {
        this.c = c;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private DayArrayAdapter dayAdapter = null;

    /**
     * 2015.3.19 17:00 时间选择进行修改 flag=1正常的起始时间
     * 在订单时间的结束时间选择上进行修改flag=12，确定时间选择器的初始化
     *
     * @author wangkun
     * @param flag
     * @return
     */
    public PopupWindow initWheelDateSelector(int flag) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.popup_date, null);

        final WheelView daysView = (WheelView) view.findViewById(R.id.day);

        final WheelView hoursView = (WheelView) view.findViewById(R.id.hour);
        final WheelView minuteView = (WheelView) view.findViewById(R.id.minute);
//		hoursView.setVisibility(View.GONE);
        final NumericWheelAdapter hourAdapter = new NumericWheelAdapter(c, 0, 23, "%02d");
        hourAdapter.setItemResource(R.layout.wheel_time);
        hourAdapter.setItemTextResource(R.id.text);
        hoursView.setViewAdapter(hourAdapter);
        hoursView.setCyclic(true);
        final IntermitNumericWheelAdapter minuteAdapter = new IntermitNumericWheelAdapter(c, 0, 50, "%02d",10);
        minuteAdapter.setItemResource(R.layout.wheel_time);
        minuteAdapter.setItemTextResource(R.id.text);
        minuteView.setViewAdapter(minuteAdapter);
        minuteView.setCyclic(true);

        // set current time
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        hoursView.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY));
        if (1 == flag || 12 == flag) {
            // dayAdapter = new DayArrayAdapter(c, calendar);// 订单根据时间查询
            dayAdapter = new DayArrayAdapter(c, -1000, 0);
        } else if (12 == flag) {
            // daysView.setCurrentItem(-14);
            // dayAdapter = new DayArrayAdapter(c, calendar);// 订单根据时间查询
            dayAdapter = new DayArrayAdapter(c, -1000, 0);// 车源发布时间
        } else {
            dayAdapter = new DayArrayAdapter(c, 0, 30);// 车源发布时间
        }

        daysView.setViewAdapter(dayAdapter);
        // 对结束时间固定到当前时间
        if (12 == flag) {
            daysView.setCurrentItem(dayAdapter.getItemsCount() - 1);
        }

        mWndDateSelector = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mWndDateSelector.setBackgroundDrawable(new BitmapDrawable(c.getResources()));
        mWndDateSelector.setFocusable(true);
        mWndDateSelector.setTouchable(true);
        mWndDateSelector.setAnimationStyle(android.R.style.Animation_Dialog);
        mWndDateSelector.update();

        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm_date);
        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // String hour = (String)
                // hourAdapter.getItemText(hoursView.getCurrentItem());
                Date day = (Date) dayAdapter.getCurrentDay(daysView.getCurrentItem());

                Date date = CommonUtil.parseDateTime(android.text.format.DateFormat.format("yyyy-MM-dd", day) + " " +
                        hourAdapter.getItemText(hoursView.getCurrentItem())+ ":"+minuteAdapter.getItemText(minuteView.getCurrentItem())+ ":00");
                getDate(date);

                mWndDateSelector.dismiss();
            }
        });
        return mWndDateSelector;
    }

    protected abstract void getDate(Date date);
}
