package tdh.ifm.android.imatch.app.update;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.CommonUtil;
import com.tdh.common.utils.SysUtil;
import com.tdh.common.widget.ShowCommonDialog;

import java.io.File;
import java.io.IOException;

import baiji.android.core.update.UpdateCenter;
import baiji.android.core.update.UpdateConstants;
import baiji.android.core.update.UpdateDownloadBarTask;
import baiji.android.core.update.UpdateException;
import baiji.android.core.update.UpdateInfo;
import baiji.android.core.update.UpdateManager;
import baiji.android.core.update.UpdateUtil;
import tdh.ifm.android.imatch.app.base.BaseApplication;
import tdh.ifm.android.imatch.app.utils.Constants;
import tdh.ifm.android.imatch.app.utils.SharedPreferencesUtil;

public class UpdateAnalyze {

    private UpdateDownloadBarTask mDownloadTask;
    protected UpdateManager mUpdateManager;
    protected File mDownloadDir;
    protected String mZipName;
    public Context context;
    public String packageName;
    public String filePath;
    public AlertDialog dialog;
    public boolean force;

    public boolean isFromWifi = false;// 是wifi的情况，dialog设置false，也就是！isFormWifi

    public UpdateAnalyze(Context context, String packageName, boolean force) {
        this.context = context;
        this.packageName = packageName;
        this.force = force;
        isFromWifi = SharedPreferencesUtil.getValueAsBoolean(Constants.UPDATE_WIFI + "_boolean", false);
        SharedPreferencesUtil.setValue(Constants.UPDATE_WIFI + "_boolean", false);
    }

    public void updateDownload(UpdateInfo info) {
        info.setUsePatch(false);
        info.setPackageName(packageName);

        // 判断使用哪种更新方式

        // 是否支持差异更新
        if (UpdateCenter.getInstance().isSupportPatchUpdate()) {

            // 是否有差异包信息
            if (info.hasPatchInfo()) {

                // 本次是否允许继续使用差异更新
                if (UpdateHelper.getInstance().isPatchEnabled()) {
                    info.setUsePatch(true);
                    APPLog.info("Decided to use patch file.");
                }
            } else {
                APPLog.info("No available patch file found.");
            }
        } else {
            APPLog.info("Patch update has been disabled!");
        }

        // 准备下载对应文件
        prepareDownload(info);

    }

    private void prepareDownload(UpdateInfo info) {
        int state = SysUtil.getNetworkState();
        try {
            mUpdateManager = UpdateHelper.getInstance().getUpdateManager();
        } catch (UpdateException e) {
            // will never come here
            return;
        }
        if (state > 0) {
            if (state == 2) {
                download(Constants.TYPE_NETWORK_CELL, info);
            } else {
                // 如果是强制更新且本地没有最新安装包，直接(后台)下载安装包
                if (info.isForceUpdate() && !hasNew(info)) {
                    downloadApk(info);
                    // 此种情况下载进度不显示
                    // noProgress = true;
                } else {
                    // 如果不是强制更新，或者本地无最新安装包，则询问下载安装包
                    download(Constants.TYPE_NETWORK_WIFI, info);
                }
            }
        } else {

            Toast.makeText(context, "当前网络不可用，请检查一下网络。", Toast.LENGTH_LONG).show();
        }
        // } else {
        //
        // Toast.makeText(context, "没有检查到SD卡，请确认SD卡是否已插入。",
        // Toast.LENGTH_LONG).show();
        // }
    }

    public void download(int networkType, final UpdateInfo info) {
        APPLog.info("UpdateActivity: Showing update prompt");
        String msg = null;

        if (networkType == Constants.TYPE_NETWORK_WIFI) {
            msg = "检查到有更新版本，是否现在就进行更新？";
        } else {
            msg = "检查到有更新版本，是否现在就进行更新（更新文件大小:" + (int) ((info.isUsePatch() ? info.getPatchFileSize() : info.getFileSize()) / 1024) + "KB）";
        }

        if (info.isUsePatch()) {
            String saved = ((info.getFileSize() - info.getPatchFileSize()) / 1024) + "KB";
            msg = msg + "\n本次使用差异更新，为您节省" + saved + "流量！";
        }
        // 判断本地是否有最新安装包或差异包
        if (hasNew(info)) {
            msg = "检测到本地有最新安装包，是否立即安装更新？";
        }

        ShowCommonDialog showCommonDialog = new ShowCommonDialog(context) {
            @Override
            public void onClickCommonDialogButtonListen(boolean confirm, Dialog dialog) {
                // TODO Auto-generated method stub
                if (confirm) {
                    ShowCommonDialog.closeCommonDialog(dialog);
                    SharedPreferencesUtil.setValue(Constants.UPDATE_SHOW, false);
                    // 本地存在最新安装包，则直接安装，否则下载安装包
                    if (hasNew(info)) {
                        try {
                            if (info.isUsePatch()) {
                                mUpdateManager.installPatch(context, new File(filePath), info);
                            } else {
                                mUpdateManager.installFile(context, new File(filePath), info);
                            }
                        } catch (UpdateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        // 如果是强制更新，则关闭程序
                        if (info.isForceUpdate()) {
                            new BaseApplication().exit();
                        }
                    } else {
                        downloadApk(info);
                    }
                } else {
                    ShowCommonDialog.closeCommonDialog(dialog);
                    SharedPreferencesUtil.setValue(Constants.UPDATE_SHOW, false);
                    // 如果是强制安装，并且本地存在最新安装包，则退出程序
                    if (info.isForceUpdate() && hasNew(info)) {
                        new BaseApplication().exit();
                    }
                }
            }
        };
        Dialog dialog = showCommonDialog.initCommonDialog(!isFromWifi);
        showCommonDialog.showConfirmDialog(dialog, null, msg, "确定更新", false);
        SharedPreferencesUtil.setValue(Constants.UPDATE_SHOW, true);
    }

    public void downloadApk(final UpdateInfo info) {
        // 如果是强制更新，则不显示toast信息
        final boolean showToast = !info.isForceUpdate();

        mDownloadDir = mUpdateManager.getFileStoreDir();

        mZipName = packageName + "-" + info.getVersion() + ".apk";
        if (info.isUsePatch()) {
            mZipName = packageName + "-" + info.getVersion() + ".patch";

        }

        if (null == mDownloadDir && showToast) {
            Toast.makeText(context, "找不到可用的存储空间", Toast.LENGTH_LONG).show();

        } else {

            final File f = new File(mDownloadDir, mZipName);
            SharedPreferencesUtil.setValue("downloadPath", f.getAbsolutePath());

            info.setFileDir(f);

            mDownloadTask = new UpdateDownloadBarTask(context, info) {

                @SuppressLint("NewApi")
                @Override
                protected void doPostDownload(final Integer result) {

                    if (UpdateConstants.DOWNLOAD_SUCC == result) {

                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected Void doInBackground(Void... params) {

                                try {
                                    if (info.isForceUpdate() && !force) {
                                        force = true;
                                        return null;
                                    }
                                    if (info.isUsePatch()) {
                                        try {
                                            mUpdateManager.installPatch(context, f, info);
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            APPLog.error("userPatch..." + e.getMessage());
                                            info.setUsePatch(false);
                                            downloadApk(info);
                                        }
                                    } else {
                                        mUpdateManager.installFile(context, f, info);
                                    }
                                } catch (UpdateException e) {

                                    // 如果本次差异更新失败，则下次自动更新时转为完全更新
                                    if (info.isUsePatch()) {
                                        UpdateHelper.getInstance().setPatchEnable(false);
                                    }
                                    if (showToast) {
                                        CommonUtil.getToast(context, e.getMessage());
                                    }
                                    APPLog.error("Install update failed, update:" + info, e);
                                }

                                return null;
                            }

                            protected void onPostExecute(Void result) {
                                if (info.isForceUpdate() && force) {
                                    download(Constants.TYPE_NETWORK_WIFI, info);
                                }
                            };

                        }.execute();
                    } else if (showToast) {
                        switch (result) {
                            case UpdateConstants.DOWNLOAD_MD5_ERR:

                                Toast.makeText(context, "校验码错误, 下载文件错误!", Toast.LENGTH_LONG).show();
                                break;
                            case UpdateConstants.DOWNLOAD_INTERRUPTED:

                                Toast.makeText(context, "下载中断!", Toast.LENGTH_LONG).show();
                                break;
                            case UpdateConstants.DOWNLOAD_NO_ENOUGH_SPACE:

                                Toast.makeText(context, "SD卡中没有足够的使用空间!", Toast.LENGTH_LONG).show();
                                break;
                            default:

                                Toast.makeText(context, "下载出现错误!", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            };

            mDownloadTask.execute();
        }
    }

    public boolean hasNew(UpdateInfo info) {
        String mergedFileMd5 = null;
        filePath = SharedPreferencesUtil.getValue("downloadPath", "");
        File file = new File(filePath);
        // 得到最新安装包的md5
        try {
            mergedFileMd5 = UpdateUtil.getFileMd5(filePath).toUpperCase();
            if (null != file && (info.getMd5().equals(mergedFileMd5) || (info.isUsePatch() && info.getPatchMd5().equals(mergedFileMd5)))) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }

    }
}
