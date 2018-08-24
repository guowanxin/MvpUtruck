package baiji.android.core.update;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StatFs;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.tdh.common.utils.APPLog;

import tdh.thunder.common.utils.CommonUtils;

public abstract class UpdateDownloadBarTask extends AsyncTask<Void, Integer, Integer> {

	private UpdateInfo updateInfo;
	private Context context;
	private int scale = 1048576;
	private NotificationManager notificationManager;
	private Notification notify;
	private int NOTIFICATION_ID = 3;
	public UpdateDownloadBarTask(Context context, UpdateInfo updateInfo) {
		this.updateInfo = updateInfo;
		this.context = context;
		Intent intent = new Intent();
		intent.setClassName(context.getPackageName(), context.getPackageName()+".ui.MainTabActivity_");
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent , 0);
		notify = new Notification();
		notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		//发送通知
		notify.icon = R.drawable.ic_launcher;
		notify.tickerText = "新版本更新中...";
		//通知栏显示所用到的布局文件
		notify.contentView = new RemoteViews(context.getPackageName(), R.layout.activity_notif_progressbar);
		notify.contentIntent = pi;

	}

	@Override
	protected void onPreExecute() {
		notificationManager.notify(NOTIFICATION_ID, notify);
	}

	@Override
	protected Integer doInBackground(Void... params) {

		String url = null;
		String md5 = null;
		long size = 0;

		if (updateInfo.isUsePatch()) {
			url = updateInfo.getPatchUrl();
			md5 = updateInfo.getPatchMd5();
			size = updateInfo.getPatchFileSize();
		} else {
			url = updateInfo.getUrl();
			md5 = updateInfo.getMd5();
			size = updateInfo.getFileSize();
		}

		if (TextUtils.isEmpty(url) || TextUtils.isEmpty(md5) || size == 0) {
			return UpdateConstants.DOWNLOAD_URL_ERR;
		}

		if (null == updateInfo.getFileDir()) {
			return UpdateConstants.DOWNLOAD_SAVE_FILE_ERR;
		}

		try {
			return download(url, md5);
		} catch (UpdateException e) {
			APPLog.error(e.getMessage(), e);
		}

		return UpdateConstants.DOWNLOAD_UNKNOWN_ERR;
	}

	private int download(String url, String md5) throws UpdateException {
		File destFile = updateInfo.getFileDir();
		int interval = 0;
		InputStream is = null;
		OutputStream os = null;
		try {
			URL _url = new URL(url);

			APPLog.info("UpdateDownloadTask:Downloading file:" + _url + " -> " + destFile.getAbsolutePath());

			URLConnection conn = _url.openConnection();

			final int lengthOfFile = conn.getContentLength();

			// 获取sdcard可剩余空间
			StatFs stat = new StatFs(destFile.getParent());
			long availSpace = ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
			if (lengthOfFile >= availSpace) {
				destFile.delete();
				return UpdateConstants.DOWNLOAD_NO_ENOUGH_SPACE;
			}

			if (lengthOfFile < 10000000) {
				scale = 1024; // if less than 10 mb, scale using kb
			}

			publishProgress(0, lengthOfFile);

			conn.connect();

			is = new BufferedInputStream(conn.getInputStream());
			os = new FileOutputStream(destFile);

			// 计算md5
			MessageDigest digest = MessageDigest.getInstance("MD5");

			byte[] buf = new byte[512];
			int nRead = -1;
			int totalRead = 0;
			while ((nRead = is.read(buf)) != -1) {
				if (this.isCancelled())
					break;
				os.write(buf, 0, nRead);
				digest.update(buf, 0, nRead);
				totalRead += nRead;

				if((totalRead*100/lengthOfFile)>interval){
					interval++;
					publishProgress(totalRead, lengthOfFile);
				}
			}

			if (isCancelled()) {
				destFile.delete();
				return UpdateConstants.DOWNLOAD_INTERRUPTED;
			}

			String localMd5 = CommonUtils.bytes2Hex(digest.digest());

			APPLog.info("UpdateDownloadTask:calculated downloaded file's md5: " + localMd5);

			if (!md5.equalsIgnoreCase(localMd5)) {

				APPLog.warn("UpdateDownloadTask:Downloaded md5 doesn't match, server's:" + md5 + " local's:" + localMd5);

				destFile.delete();
				return UpdateConstants.DOWNLOAD_MD5_ERR;
			}

			if (destFile.exists()) {
				APPLog.info("UpdateDownloadTask:Downloaded successfully.");
			}

			return UpdateConstants.DOWNLOAD_SUCC;

		} catch (IOException e) {

			APPLog.error("UpdateDownloadTask:Error while downloading update file", e);
			destFile.delete();

			return UpdateConstants.DOWNLOAD_IO_ERR;

		} catch (Exception e) {
			APPLog.error("UpdateDownloadTask:Error while downloading update file", e);

			destFile.delete();

			throw new UpdateException(e);
		} finally {

			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
					APPLog.warn("UpdateDownloadTask:Error while closing InputStream", e);
				}
			}
			if (null != os) {
				try {
					os.flush();
					os.close();
				} catch (Exception e) {
					APPLog.warn("UpdateDownloadTask:Error while closing OutputStream", e);
				}
			}
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		notificationManager.cancel(NOTIFICATION_ID);

		doPostDownload(result);
	}

	protected abstract void doPostDownload(Integer result);

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (null == notificationManager||null==notify){
			return;
		}
		if (null==values||values.length<2) {
			notify.contentView.setProgressBar(R.id.content_progress, 100, 100, true);
			notificationManager.notify(NOTIFICATION_ID, notify);
			return;
		}
		notify.contentView.setTextViewText(R.id.content_text, Integer.parseInt((100*values[0]/values[1])+"")+"%");
		notify.contentView.setProgressBar(R.id.content_progress, values[1], values[0], false);
		notificationManager.notify(NOTIFICATION_ID, notify);
	}
}
