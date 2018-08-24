package com.tdh.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.prefs.Preferences;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by TDH on 2016/12/16.
 */

public class AddCookiesInterceptor implements Interceptor {

    String TAG = "utruck";
    private Context context;
    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        String cookie = sharedPreferences.getString("cookie", "");
        Request request = chain.request();
        Response response;
        if (!cookie.equals("")) {
            Log.e(TAG, "我添加上cookie了");
            Log.e("utruck", "cookie: "+cookie);
            Request compressedRequest = request.newBuilder()
                    .header("Content-type","application/x-www-form-urlencoded; charset=UTF-8")
                    .header("X-Requested-With","XMLHttpRequest")
                    .header("Cookie", cookie)
                    .build();

            response = chain.proceed(compressedRequest);
        }else{
            Log.e(TAG, "我还是没有添加cookie");
            response = chain.proceed(request);
        }
        return response;
    }
}
