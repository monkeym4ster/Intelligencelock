package com.ziroom.developer.httpclient.http.callback;

import java.io.IOException;

import com.ziroom.developer.httpclient.http.HttpClientResponse;

/**
 * 
 * 响应Response
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:02:43
 * @since 1.0.0
 */
public abstract class CallbackAsResponse extends HttpClientCallback {

    public CallbackType getType() {
        return CallbackType.RESPONSE;
    }

    public abstract void exec(final HttpClientResponse response) throws IOException;
}
