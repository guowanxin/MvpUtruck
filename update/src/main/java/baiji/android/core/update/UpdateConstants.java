package baiji.android.core.update;

public interface UpdateConstants {

	String PREFS_UPDATE = "baiji.android.update";
	String OTA_VER_PROP = "bjrom.otaver";

	long ROM_CHECK_INTERVAL = 12 * 60 * 60 * 1000;
	long APP_CHECK_INTERVAL = 6 * 60 * 60 * 1000;

	String KEY_ROM_LAST_CHECK_TIME = "ROM_CHECK_TIME";
	String KEY_APP_LAST_CHECK_TIME_PREFIX = "APP_CHECK_TIME";

	int DOWNLOAD_SUCC = 0;
	int DOWNLOAD_MD5_ERR = 1;
	int DOWNLOAD_INTERRUPTED = 2;
	int DOWNLOAD_NO_ENOUGH_SPACE = 3;
	int DOWNLOAD_URL_ERR = 4;
	int DOWNLOAD_SAVE_FILE_ERR = 5;
	int DOWNLOAD_IO_ERR = 6;
	int DOWNLOAD_UNKNOWN_ERR = -1;

	String BJ_BASE = "bjsoft";
	String UPDATE_ROM_PACKAGE_PATH = BJ_BASE + "/ota/";
	String UPDATE_APP_PACKAGE_PATH = BJ_BASE + "/app/";

	String PATCH_EXT = ".patch";

}
