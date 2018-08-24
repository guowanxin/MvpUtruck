package tdh.ifm.android.imatch.app.update;


import android.content.Context;
import android.os.AsyncTask;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;

import baiji.android.core.update.UpdateException;
import baiji.android.core.update.UpdateInfo;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;

public class CheckUpdateTask extends AsyncTask<Void, Void, UpdateInfo> {

    // 消息
    private String errmsg;
    private Context mContext;

    // 是否是强制检查
    private boolean isForce;

    // 是否是强制更新
    private boolean isForceUpdate = false;

    // 是否是强制更新
    private boolean isToast = false;

    public CheckUpdateTask(Context context, boolean force) {
        this.mContext = context;
        this.isForce = force;
        this.isForceUpdate = true;
//		int state = SysUtil.getNetworkState();
//		if (state > 0) {
//			if (state != 2) {
//				this.isForceUpdate = true;
//			}
//		}
    }

    public CheckUpdateTask(Context context, boolean force,boolean isToast) {
        this.mContext = context;
        this.isForce = force;
        this.isToast = isToast;
    }

    @Override
    protected void onPostExecute(UpdateInfo result) {
        super.onPostExecute(result);

        if (null != result) {
            new UpdateAnalyze(mContext, mContext.getPackageName(), isForce).updateDownload(result);
        } else if (isForce) {
            if (isToast) {
                return;
            }
            SharedPreferencesUtil.setValue(Constants.UPDATE_WIFI + "_boolean", false);
            if (null == errmsg) {
                CommonUtil.getToast(mContext, "当前已经是最新版本");
            } else {
                CommonUtil.getToast(mContext, errmsg);
            }
        }
    }

    @Override
    protected UpdateInfo doInBackground(Void... params) {
        try {
            return UpdateHelper.getInstance().checkUpdate(isForce,
                    SharedPreferencesUtil.getValueAsBoolean("isForceUpdate", isForceUpdate));
        } catch (UpdateException e) {
            errmsg = e.getMessage();
            APPLog.error(errmsg, e);
        }
        return null;
    }

}
