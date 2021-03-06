package tdh.ifm.android.imatch.app.base;

import android.content.Context;

import com.google.gson.Gson;
import com.tdh.retrofit.AddCookiesInterceptor;
import com.tdh.retrofit.GetCookiesInterceptor;
import com.tdh.retrofit.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tdh.ifm.android.imatch.app.model.Api;
import tdh.ifm.android.imatch.app.utils.NetContant;

/**
 * Author：gwx
 * Create at：2017/4/24 19:08
 */
public class BaseNet {

    private static final String BASE_URL = NetContant.HOST_URL;

    private static final int DEFAULT_TIMEOUT = 30;

    protected Retrofit retrofit;

    protected Api api;

    protected Gson gson;
    //构造方法私有
    protected BaseNet(Context context) {
        gson = new Gson();
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        LogInterceptor logInterceptor = new LogInterceptor();
        httpClientBuilder.addInterceptor(logInterceptor)
                .addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new GetCookiesInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        api = retrofit.create(Api.class);
    }

    public RequestBody getBody(String json) {
        RequestBody body=RequestBody.create(okhttp3.MediaType.
                parse("application/json; charset=utf-8"),json);
        return body;
    }
}
