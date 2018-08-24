package tdh.ifm.android.imatch.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tdh.common.utils.SharedAppContext;

/**
 * Created by gwx on 2016/12/20.
 */

public class SharedPreferencesUtil {

    public static final String PREFS_GLOBAL = SharedAppContext.getContentContext().getPackageName();

    public static SharedPreferences getSharedPreferences(String name) {
        Context context = SharedAppContext.getContentContext();
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 设值到PREFS_GLOBAL
     *
     * @param key
     * @param value
     */
    public static void setValue(String key, String value) {
        setValue(PREFS_GLOBAL, key, value);
    }

    /**
     * 设值到PREFS_GLOBAL
     *
     * @param key
     * @param value
     */
    public static void setValue(String key, int value) {
        setValue(PREFS_GLOBAL, key, value);
    }

    /**
     * 设值到PREFS_GLOBAL
     *
     * @param key
     * @param value
     */
    public static void setValue(String key, float value) {
        setValue(PREFS_GLOBAL, key, value);
    }

    /**
     * 设值到PREFS_GLOBAL
     *
     * @param key
     * @param value
     */
    public static void setValue(String key, long value) {
        setValue(PREFS_GLOBAL, key, value);
    }

    /**
     * 设值到PREFS_GLOBAL
     *
     * @param key
     * @param value
     */
    public static void setValue(String key, boolean value) {
        setValue(PREFS_GLOBAL, key, value);
    }

    public static void setValue(String prefName, String key, String value) {
        SharedPreferences pref = getSharedPreferences(prefName);
        pref.edit().putString(key, value).commit();
    }

    public static void setValue(String prefName, String key, int value) {
        SharedPreferences pref = getSharedPreferences(prefName);
        pref.edit().putInt(key, value).commit();
    }

    public static void setValue(String prefName, String key, float value) {
        SharedPreferences pref = getSharedPreferences(prefName);
        pref.edit().putFloat(key, value).commit();
    }

    public static void setValue(String prefName, String key, long value) {
        SharedPreferences pref = getSharedPreferences(prefName);
        pref.edit().putLong(key, value).commit();
    }

    public static void setValue(String prefName, String key, boolean value) {
        SharedPreferences pref = getSharedPreferences(prefName);
        pref.edit().putBoolean(key, value).commit();
    }

    public static String getValue(String key, String defaultValue) {
        return getValue(PREFS_GLOBAL, key, defaultValue);
    }

    public static int getValueAsInt(String key, int defaultValue) {
        return getValueAsInt(PREFS_GLOBAL, key, defaultValue);
    }

    public static boolean getValueAsBoolean(String key, boolean defaultValue) {
        return getValueAsBoolean(PREFS_GLOBAL, key, defaultValue);
    }

    public static long getValueAsLong(String key, long defaultValue) {
        return getValueAsLong(PREFS_GLOBAL, key, defaultValue);
    }

    public static float getValueAsFloat(String key, float defaultValue) {
        return getValueAsFloat(PREFS_GLOBAL, key, defaultValue);
    }

    public static String getValue(String prefName, String key, String defaultValue) {
        return getSharedPreferences(prefName).getString(key, defaultValue);
    }

    public static int getValueAsInt(String prefName, String key, int defaultValue) {
        return getSharedPreferences(prefName).getInt(key, defaultValue);
    }

    public static boolean getValueAsBoolean(String prefName, String key, boolean defaultValue) {
        return getSharedPreferences(prefName).getBoolean(key, defaultValue);
    }

    public static long getValueAsLong(String prefName, String key, long defaultValue) {
        return getSharedPreferences(prefName).getLong(key, defaultValue);
    }

    public static float getValueAsFloat(String prefName, String key, float defaultValue) {
        return getSharedPreferences(prefName).getFloat(key, defaultValue);
    }

}
