package baiji.android.core.update;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
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
import android.content.pm.PackageInfo;
import android.os.PowerManager;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.tdh.common.utils.APPLog;
import com.tdh.common.utils.SysUtil;

public class RomUpdateManager implements UpdateManager {

	@Override
	public boolean isRoutineCheckRequired(Context context, String packageName) {
		long lastCheckTime = UpdateUtil.getPrefValueAsLong(context, UpdateConstants.KEY_ROM_LAST_CHECK_TIME, 0);

		long currentTime = System.currentTimeMillis();
		long diff = currentTime - lastCheckTime;

		if (APPLog.isInfoable()) {
			APPLog.info("RomUpdateManager: Previous check time:"
					+ DateFormat.format("yyyy/MM/dd kk:mm:ss", lastCheckTime));
			APPLog.info("RomUpdateManager: Check interval:" + (UpdateConstants.ROM_CHECK_INTERVAL / 1000) + "seconds");
		}

		if (lastCheckTime > 0 && diff < UpdateConstants.ROM_CHECK_INTERVAL) {
			// 不需要再去检查更新了
			return false;
		}
		return true;
	}

	@Override
	public File getFileStoreDir() {
		File extpath = SysUtil.getRealExternalStorage();

		if (null == extpath) {
			APPLog.error("UpdateManager:SysUtil.getRealExternalStorage() is null.");
		} else {
			File f = new File(extpath, UpdateConstants.UPDATE_ROM_PACKAGE_PATH);

			if (f.exists() && f.isDirectory()) {
				return f;
			} else {
				boolean created = f.mkdirs();

				if (!created) {
					APPLog.error("UpdateManager:Could not create directory:" + f.getAbsolutePath());
					return null;
				} else {
					return f;
				}
			}
		}

		return null;
	}

	@Override
	public UpdateInfo checkUpdate(String url, Map<String, String> parameters, Context context, String packageName)
			throws UpdateException {

		APPLog.info("UpdateManager:Requesting url:" + url);

		String json = null;
		UpdateInfo info = null;

		try {
			HttpEntityEnclosingRequestBase httpRequest = new HttpPost(url);

			if (null == parameters) {
				parameters = new HashMap<String, String>();
			}

			parameters.put("version", getCurrentVersion());
			parameters.put("imei", SysUtil.getDefaultIMEI());
			parameters.put("iccid", SysUtil.getDefaultICCID());
			parameters.put("model", android.os.Build.MODEL);

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

			HttpResponse httpResponse = new DefaultHttpClient(httpParams).execute(httpRequest);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				json = EntityUtils.toString(httpResponse.getEntity());
				APPLog.info("UpdateManager:Response:" + json);
			} else {
				throw new UpdateException("UpdateManager:checkUpdate, response not OK! Code=" + statusCode);
			}

			UpdateUtil.setPrefValue(context, UpdateConstants.KEY_ROM_LAST_CHECK_TIME, System.currentTimeMillis());

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
			info.setReinstall(obj.getBoolean("reinstall"));
			info.setUrl(obj.getString("url"));
			info.setFileName(obj.getString("name"));
			info.setPatchName(obj.getString("patchname"));
			if(obj.has("isForceUpdate")){
				info.setForceUpdate(obj.getBoolean("isForceUpdate"));
			}else{
				info.setForceUpdate(false);
			}
			String apkinfo = obj.getString("apkinfo");

			// 将apk信息放人map,apk信息包含最新的apk的md5值
			Map<String, String> apkInfoMap = new HashMap<String, String>();

			String[] infos = apkinfo.trim().split("\n");

			for (String s : infos) {

				if (TextUtils.isEmpty(s)) {
					continue;
				}

				s = s.trim();

				String[] pair = s.split(",");

				if (2 != pair.length) {
					continue;
				}

				apkInfoMap.put(pair[0].trim(), pair[1].trim());
			}

			info.setPatchedApks(apkInfoMap);

		} catch (Exception e) {
			throw new UpdateException("UpdateManager:checkUpdate err", e);
		}

		return info;
	}

	private String getCurrentVersion() {
		return UpdateUtil.getSystemProp(UpdateConstants.OTA_VER_PROP);
	}

	@Override
	public void installFile(Context context, File file, UpdateInfo updateInfo) {

		APPLog.info("Installing update file...");

		Process p;
		try {
			p = Runtime.getRuntime().exec("sh");
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			os.writeBytes("rm -f /cache/recovery/extendedcommand\n");
			os.writeBytes("echo 'ui_print(\"Baiji Cheyunbao ROM Update Program\");' >> /cache/recovery/extendedcommand\n");

			// 在recovery模式下，存储卡都挂载为/sdcard
			os.writeBytes("echo 'assert(install_zip(\"/sdcard"
					+ file.getAbsolutePath().replace(SysUtil.getRealExternalStorage().getAbsolutePath(), "") + "\"));'"
					+ " >> /cache/recovery/extendedcommand\n");

			os.writeBytes("exit\n");
			os.flush();
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				APPLog.error("UpdateActivity:Creation of install file interrupted", e);
			}
		} catch (IOException e1) {
			APPLog.error("UpdateActivity:Error while creating install file", e1);
		}

		((PowerManager) context.getSystemService(Context.POWER_SERVICE)).reboot("recovery");
	}

	@Override
	public void installPatch(Context context, File file, UpdateInfo updateInfo) throws UpdateException {

		APPLog.info("Installing update patch...");

		// 准备目录
		// temp用于存放解压缩出来的文件和处理合并文件
		// install用于存放合并完后的zip文件
		File parent = getFileStoreDir();
		File tempDir = null;
		try {
			tempDir = new File(parent, "temp");
			if (tempDir.exists()) {
				UpdateUtil.delete(tempDir);
			}
			tempDir.mkdir();

			File installFile = new File(parent, "install/update.zip");
			if (installFile.exists()) {
				UpdateUtil.delete(installFile);
			} else {
				installFile.getParentFile().mkdirs();
			}
			// 解压缩
			UpdateUtil.unzip(file.getAbsolutePath(), tempDir.getAbsolutePath());

			File sysAppDir = new File(tempDir, "system/app/");

			File[] apkpatches = sysAppDir.listFiles();

			// 如果目录是空的，直接install，不过这个差异包没有任何意义
			if (apkpatches.length == 0) {
				installFile(context, file, updateInfo);
			} else {
				// 合并文件，默认只合并/system/app/*.apk文件, 如果差异文件与本地的不能完全对应则差异更新只能中断
				for (File patch : apkpatches) {
					// merge并验证merge后文件的md5

					if (!patch.getName().endsWith(UpdateConstants.PATCH_EXT)) {
						continue;
					}

					String apkPath = patch.getAbsolutePath().replace(UpdateConstants.PATCH_EXT, ".apk");

					File apkFile = new File(apkPath);

					if (null == updateInfo.getPatchedApks()) {
						throw new UpdateException("Could not find apk info for this patch:" + patch.getName());
					}

					String apkMd5 = updateInfo.getPatchedApks().get(apkFile.getName());

					if (null == apkMd5) {
						throw new UpdateException("Could not find apk info for this patch:" + patch.getName());
					}

					// 获取当前版本的apk文件信息
					PackageInfo pack = UpdateUtil.findSystemApk(context, apkFile.getName());

					// 如果没有找到apk,那么可以认为该系统已经被破坏了,也就不能使用差异更新了,只能重新完整升级
					if (null == pack) {
						throw new UpdateException("/system/app下找不到原始文件:" + apkFile.getName());
					}

					UpdateUtil.mergeApk(context, patch, pack.applicationInfo.sourceDir, apkFile.getAbsolutePath(),
							apkMd5);

					// 删除patch文件
					patch.delete();
				}

				// 重新压缩成zip
				UpdateUtil.zip(tempDir.getAbsolutePath(), installFile.getAbsolutePath(), false);

				// 调用install方法
				installFile(context, installFile, updateInfo);
			}

		} catch (Exception e) {
			throw new UpdateException(e);
		} finally {
			// 清除临时目录，这里只清除解压缩出来的文件，不会清除下载的文件
			if (null != tempDir) {
				try {
					UpdateUtil.delete(tempDir);
				} catch (IOException e) {
				}
			}
		}

	}

	public void installPatchSilent(Context context, File patchFile, UpdateInfo updateInfo) throws UpdateException {
	}

	public void installFileSilent(Context context, File file, UpdateInfo updateInfo) {
	}

}
