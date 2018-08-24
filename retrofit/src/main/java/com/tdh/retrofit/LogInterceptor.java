package com.tdh.retrofit;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 采集okhttp3client的日志
 * Created by NIUDEYANG on 2016/11/18.
 */

public class LogInterceptor implements Interceptor {
    String TAG = "utruck";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.e(TAG, "request:" + request.toString());
        RequestBody requestBody = request.body();
        if(requestBody!= null) {
            StringBuilder sb = new StringBuilder("Request Body [");
            okio.Buffer buffer = new okio.Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            if(isPlaintext(buffer)){
                sb.append(buffer.readString(charset));
                sb.append(" (Content-Type = ").append(contentType.toString()).append(",")
                        .append(requestBody.contentLength()).append("-byte body)");
            }else {
                sb.append(" (Content-Type = ").append(contentType.toString())
                        .append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
            }
            sb.append("]");
            Log.e(TAG, String.format(Locale.getDefault(), "%s", sb.toString()));
        }
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        Log.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        //String content = response.body().string();
        /**
         * 在调用了response.body().string()方法之后，response中的流会被关闭，
         * 我们需要创建出一个新的response给应用层处理通过response.peekBody()方法复制了一个ResponseBody，
         * 使用这个临时的ResponseBody来打印body的内容，而不是直接使用response.body().string()
         原文链接：http://www.jianshu.com/p/d836271b1ae4
         参考http://blog.csdn.net/it_talk/article/details/51734507
         著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。*/
        ResponseBody originalBody = response.body();
        Log.e(TAG, "response code:" + response.code());
        String content = originalBody.string();

        Log.e(TAG, "response body:" + content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    static boolean isPlaintext(Buffer buffer){
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }


}