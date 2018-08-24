package com.tdh.common.adapter;

import android.content.Context;

/**
 * Author：gwx
 * Create at：2017/3/15 14:37
 */
public class IntermitNumericWheelAdapter extends AbstractWheelTextAdapter{

    /** The default min value */
    public static final int DEFAULT_MAX_VALUE = 9;

    /** The default max value */
    private static final int DEFAULT_MIN_VALUE = 0;

    // Values
    private int minValue;
    private int maxValue;
    //间隔数
    private int count;

    // format
    private String format;

    /**
     * Constructor
     * @param context the current context
     */
    public IntermitNumericWheelAdapter(Context context) {
        this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public IntermitNumericWheelAdapter(Context context, int minValue, int maxValue) {
        this(context, minValue, maxValue, null,1);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     * @param format the format string
     */
    public IntermitNumericWheelAdapter(Context context, int minValue, int maxValue, String format, int count) {
        super(context);

        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
        this.count = count;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index * count;
            return format != null ? String.format(format, value) : Integer.toString(value);
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return (maxValue - minValue) / count + 1;
    }

}
