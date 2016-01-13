package com.ziroom.developer.httpclient.http.callback;

import java.io.IOException;

/**
 * 
 * 响应状态
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:03:52
 * @since 1.0.0
 */
public abstract class CallbackAsStatusCode extends HttpClientCallback {

    public CallbackType getType() {
        return CallbackType.STATUS_CODE;
    }

    public abstract void exec(final int code) throws IOException;
}
