package com.tdh.common.widget;

/**
 * Author：TDH
 * Create at：2017/3/15 14:18
 */
public interface OnWheelClickedListener {
    /**
     * Callback method to be invoked when current item clicked
     * @param wheel the wheel view
     * @param itemIndex the index of clicked item
     */
    void onItemClicked(WheelView wheel, int itemIndex);
}
