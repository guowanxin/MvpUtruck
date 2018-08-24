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

import tdh.thunder.common.utils.CommonUtils;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StatFs;
import android.text.TextUtils;

import com.tdh.common.utils.APPLog;

public abstract class UpdateDownloadTask extends AsyncTask<Void, Integer, Integer> {

	private UpdateInfo updateInfo;
	private ProgressDialog progressDialog;
	private WakeLock wakeLock;
	private Context context;
	private int scale = 1048576;
	public static final int WAKE_TIMEOUT = 30000;

	public UpdateDownloadTask(Context context, UpdateInfo updateInfo) {
		this.updateInfo = updateInfo;
		this.context = context;

		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("下载升级包");
		progressDialog.setMessage("升级包正在下载，请稍等...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setProgress(0);

		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, UpdateDownloadTask.class.getName());
	}

	@Override
	protected void onPreExecute() {
		progressDialog.show();
		wakeLock.acquire();
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

			byte[] buf = new byte[4096];
			int nRead = -1;
			int totalRead = 0;
			while ((nRead = is.read(buf)) != -1) {
				if (this.isCancelled())
					break;
				os.write(buf, 0, nRead);
				digest.update(buf, 0, nRead);
				totalRead += nRead;
				publishProgress(totalRead, lengthOfFile);
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
		progressDialog.dismiss();
		wakeLock.release();

		doPostDownload(result);
	}

	protected abstract void doPostDownload(Integer result);

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (progressDialog == null)
			return;

		if (values[0] == -1) {
			progressDialog.setIndeterminate(true);
			return;
		}

		progressDialog.setIndeterminate(false);

		if (values.length == 0)
			return;
		progressDialog.setProgress((values[0] / scale));
		if (values.length == 1)
			return;
		progressDialog.setMax(values[1] / scale);
	}

}
