package com.lsj.http;

/**
 * Created by SmallApple on 2017/3/20.
 */
import java.io.InputStream;

public interface HttpParams {
    public String send2String(String baseUrl) throws Exception;
    public InputStream send2InputStream(String baseUrl) throws Exception;
    public HttpParams put(String key, String value);
}

