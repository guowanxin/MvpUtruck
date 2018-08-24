package baiji.android.core.update;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.format.DateFormat;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.FileUtil;
import com.tdh.common.utils.SysUtil;

public class AppUpdateManger implements UpdateManager {

	@Override
	public boolean isRoutineCheckRequired(Context context, String packageName) {
		long lastCheckTime = UpdateUtil.getPrefValueAsLong(context, UpdateConstants.KEY_APP_LAST_CHECK_TIME_PREFIX + "_" + packageName, 0);

		long currentTime = System.currentTimeMillis();
		long diff = currentTime - lastCheckTime;

		if (APPLog.isInfoable()) {
			APPLog.info("AppUpdateManger: Previous check time:" + DateFormat.format("yyyy/MM/dd kk:mm:ss", lastCheckTime));
			APPLog.info("AppUpdateManger: Check interval:" + (UpdateConstants.ROM_CHECK_INTERVAL / 1000) + "seconds");
		}

		if (lastCheckTime > 0 && diff < UpdateConstants.APP_CHECK_INTERVAL) {
			// 锟斤拷锟斤拷要锟斤拷去锟斤拷锟斤拷锟斤拷锟斤拷
			return false;
		}
		return true;
	}

	@Override
	public File getFileStoreDir() {
		File extpath = SysUtil.getRealExternalStorage();

		if (null == extpath) {
			APPLog.error("AppUpdateManger:SysUtil.getRealExternalStorage() is null.");
		} else {
			File f = new File(extpath, UpdateConstants.UPDATE_APP_PACKAGE_PATH);

			if (f.exists() && f.isDirectory()) {
				return f;
			} else {
				boolean created = f.mkdirs();

				if (!created) {
					APPLog.error("AppUpdateManger:Could not create directory:" + f.getAbsolutePath());
					return null;
				} else {
					return f;
				}
			}
		}

		return null;
	}

	@Override
	public UpdateInfo checkUpdate(String url, Map<String, String> parameters, Context context, String packageName) throws UpdateException {
		APPLog.info("AppUpdateManger:Requesting url:" + url);

		String json = null;
		UpdateInfo info = null;

		try {
			HttpEntityEnclosingRequestBase httpRequest = new HttpPost(url);

			if (null == parameters) {
				parameters = new HashMap<String, String>();
			}

			parameters.put("type", "1");
			parameters.put("pkgName", packageName);
			parameters.put("version", getCurrentVersion(context, packageName));

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			Iterator<String> iter = parameters.keySet().iterator();

			for (; iter.hasNext();) {
				String key = iter.next();
				params.add(new BasicNameValuePair(key, parameters.get(key)));
			}

			httpRequest.setEntity(new UrlEncodedFormEntity(params, "GBK"));

			// set connection timeout
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);

			DefaultHttpClient client = new DefaultHttpClient(httpParams);

			HttpResponse httpResponse = client.execute(httpRequest);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				json = EntityUtils.toString(httpResponse.getEntity());
				APPLog.info("UpdateManager:Response:" + json);
			} else {
				throw new UpdateException("UpdateManager:checkUpdate, response not OK! Code=" + statusCode);
			}

			UpdateUtil.setPrefValue(context, UpdateConstants.KEY_APP_LAST_CHECK_TIME_PREFIX + "_" + packageName, System.currentTimeMillis());

			JSONObject jsonObject = new JSONObject(json);

			if (jsonObject.isNull("obj")) {
				return null;
			}

			JSONObject obj = jsonObject.getJSONObject("obj");

			info = new UpdateInfo();
			info.setFileSize(obj.getLong("size"));
			info.setMd5(obj.getString("md5"));
			info.setUrl(obj.getString("url"));
			info.setVersion(obj.getString("version"));
			info.setPatchFileSize(obj.getLong("patchsize"));
			info.setPatchMd5(obj.getString("patchmd5"));
			info.setPatchUrl(obj.getString("patchurl"));
			info.setUrl(obj.getString("url"));
			if (obj.has("isForceUpdate")) {
				info.setForceUpdate(obj.getBoolean("isForceUpdate"));
			} else {
				info.setForceUpdate(false);
			}
		} catch (Exception e) {
			throw new UpdateException("Error while checking update", e);
		}

		return info;
	}

	public String getCurrentVersion(Context context, String packageName) {
		PackageInfo pack = findApk(context, packageName);

		if (null != pack) {
			return String.valueOf(pack.versionCode);
		}

		return null;
	}

	public PackageInfo findApk(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packs = packageManager.getInstalledPackages(0);

		for (PackageInfo pack : packs) {

			if (pack.packageName.equals(packageName)) {

				String dir = pack.applicationInfo.sourceDir;

				// if (!dir.startsWith("/system/")) {
				// return pack;
				// }

				return pack;
			}

		}

		return null;
	}

	@Override
	public void installFile(Context context, File file, UpdateInfo updateInfo) {
//		Intent promptInstall = new Intent(Intent.ACTION_VIEW);
//		promptInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//		promptInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//		context.startActivity(promptInstall);
		FileUtil.startActionFile(context,file,"application/vnd.android.package-archive");
	}

	@Override
	public void installPatch(Context context, File patchFile, UpdateInfo updateInfo) throws UpdateException {

		try {
			File fullApk = mergePath(context, patchFile, updateInfo);

			installFile(context, fullApk, updateInfo);
		} catch (Exception e) {
			throw new UpdateException(e);
		}

	}

	private File mergePath(Context context, File patchFile, UpdateInfo updateInfo) throws UpdateException {
		if (!updateInfo.hasPatchInfo()) {
			throw new UpdateException("没锟叫诧拷锟斤拷锟侥硷拷锟斤拷息锟斤拷锟斤拷锟斤拷使锟矫诧拷锟斤拷锟斤拷锟�");
		}

		APPLog.info("Installing update patch...");

		// 准锟斤拷目录
		// temp锟斤拷锟节达拷藕喜锟斤拷募锟�
		File parent = getFileStoreDir();
		File tempDir = null;
		try {
			String apkName = updateInfo.getPackageName() + ".apk";

			tempDir = new File(parent, updateInfo.getPackageName());

			if (tempDir.exists()) {
				UpdateUtil.delete(tempDir);
			}
			tempDir.mkdir();

			File apkFile = new File(tempDir, apkName);

			String apkMd5 = updateInfo.getMd5();

			if (null == apkMd5) {
				throw new UpdateException("校锟斤拷锟斤拷缺失:" + updateInfo);
			}

			PackageInfo pack = findApk(context, updateInfo.getPackageName());

			if (null == pack) {
				throw new UpdateException("没锟斤拷锟揭碉拷锟斤拷锟截碉拷apk锟斤拷息");
			}

			UpdateUtil.mergeApk(context, patchFile, pack.applicationInfo.sourceDir, apkFile.getAbsolutePath(), apkMd5);

			// 删锟斤拷patch锟侥硷拷
			patchFile.delete();

			return apkFile;

		} catch (Exception e) {
			throw new UpdateException(e);
		}
	}

	// 锟斤拷默锟斤拷装:root锟斤拷锟斤拷锟借备选锟斤拷默锟斤拷装锟斤拷没锟斤拷root锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷装
	@Override
	public void installFileSilent(Context context, File file, UpdateInfo updateInfo) {
		// PackageUtils.install(context, file.getPath());
		PackageUtils.installSilent(context, file.getPath());
	}

	@Override
	public void installPatchSilent(Context context, File patchFile, UpdateInfo updateInfo) throws UpdateException {

		try {
			File fullApk = mergePath(context, patchFile, updateInfo);

			installFileSilent(context, fullApk, updateInfo);
		} catch (Exception e) {
			throw new UpdateException(e);
		}

	}
}
