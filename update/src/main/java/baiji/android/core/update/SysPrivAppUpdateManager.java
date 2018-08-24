package baiji.android.core.update;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import android.content.Context;

import com.tdh.common.utils.APPLog;

public class SysPrivAppUpdateManager extends AppUpdateManger implements UpdateManager {

	@Override
	public void installFileSilent(Context context, File file, UpdateInfo updateInfo) {

		// String paramString = "pm install -r " + file.getAbsolutePath();
		//
		// send_key_touch(paramString);

		install3(file.getAbsolutePath());
	}


	public void install3(String path) {
		Process install = null;
		try {
			// 安装apk命令
			install = Runtime.getRuntime().exec("pm install -r " + path);
			install.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
			APPLog.error(e.getMessage(), e);
		} finally {
			if (install != null) {
				install.destroy();
			}

		}
	}

	private void send_key_touch(String keycode) {
		try {
			String keyCommand = keycode;
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(keyCommand);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("安装出异常了");
			e.printStackTrace();
		}
	}

	public void install2(String path) {
		APPLog.info("Installing " + path);

		Process p;
		try {
			p = Runtime.getRuntime().exec("sh");
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			os.writeBytes("pm install -r " + path);
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

	}

	public String install(String apkAbsolutePath) {
		String[] args = { "pm", "install", "-r", apkAbsolutePath };
		String result = "";
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();

			baos.write("error:\n".getBytes());

			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			baos.write("\n info:\n".getBytes());

			inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);

			APPLog.warn(result);

			process.waitFor();

		} catch (IOException e) {
			APPLog.error(e.getMessage(), e);
		} catch (Exception e) {
			APPLog.error(e.getMessage(), e);
		} finally {
			try {
				if (errIs != null) {
					errIs.close();
				}
				if (inIs != null) {
					inIs.close();
				}
			} catch (IOException e) {
				APPLog.error(e.getMessage(), e);
			}
			if (process != null) {
				process.destroy();
			}
		}
		return result;
	}

}
