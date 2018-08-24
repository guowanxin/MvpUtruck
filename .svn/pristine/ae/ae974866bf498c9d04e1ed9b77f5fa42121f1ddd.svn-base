package tdh.ifm.android.imatch.app.update;

import android.content.Context;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.SharedAppContext;

import java.util.HashMap;
import java.util.Map;

import baiji.android.core.update.UpdateCenter;
import baiji.android.core.update.UpdateException;
import baiji.android.core.update.UpdateInfo;
import baiji.android.core.update.UpdateManager;
import tdh.ifm.android.imatch.app.utils.NetContant;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;

/**
 * <pre>
 * Description: 更新的帮助类，负责获取UpdateManager去检查和完成更新。可在此实现UpdateManager没有提供的个性化功能。
 * Copyright:	Copyright (c)2012
 * Company:		百及科技
 * Author:		bravo
 * Create at:	2013-8-29 上午10:50:07
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 *
 * </pre>
 */
public class UpdateHelper {

    private static UpdateHelper INSTANCE = new UpdateHelper();
    private UpdateManager mUpdateManager;
    private Context mContext;

    private UpdateHelper() {
        this.mContext = SharedAppContext.getContentContext();
    }

    public static UpdateHelper getInstance() {
        return INSTANCE;
    }

    public UpdateInfo checkUpdate(boolean force, boolean forceUpdate) throws UpdateException {
        //当不是强制检查且不是强制刷新时检查是否需要更新
        if (!force&&!forceUpdate) {
            boolean required = getUpdateManager().isRoutineCheckRequired(mContext, mContext.getPackageName());

            if (!required) {
                APPLog.info("UpdateManager: Update check canceled due to the check interval limit.");
                return null;
            }
        }

        Map<String, String> param = new HashMap<String, String>();

        String pkgName = mContext.getPackageName();

        UpdateInfo info = getUpdateManager().checkUpdate(NetContant.UPDATE_URL, param, mContext,
                pkgName);
        SharedPreferencesUtil.setValue("isForceUpdate",null==info ? false:info.isForceUpdate());
        // 没什么可更新的，reset to true
        if (null == info && !UpdateHelper.getInstance().isPatchEnabled()) {
            UpdateHelper.getInstance().setPatchEnable(true);
        }

        return info;
    }

    /**
     * 自动更新时是否可以使用差异更新，默认为true
     *
     * @return
     */
    public boolean isPatchEnabled() {
        return SharedPreferencesUtil.getValueAsBoolean(KEY_PATCH_ENABLED, true);
    }

    public static final String KEY_PATCH_ENABLED = "PATCH_ENABLED";

    /**
     * 设置自动更新时是否可以使用差异更新
     *
     * @param enable
     */
    public void setPatchEnable(boolean enable) {
        SharedPreferencesUtil.setValue(KEY_PATCH_ENABLED, enable);
    }

    public UpdateManager getUpdateManager() throws UpdateException {

        if (null == mUpdateManager) {
            mUpdateManager = UpdateCenter.getInstance().getUpdateManager(mContext, mContext.getPackageName());
        }

        return mUpdateManager;
    }

}
