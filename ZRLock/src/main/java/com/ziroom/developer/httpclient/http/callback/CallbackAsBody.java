package com.ziroom.developer.httpclient.http.callback;

import java.io.IOException;

/**
 * 响应内容体
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午3:54:59
 * @since 1.0.0
 */
public abstract class CallbackAsBody extends HttpClientCallback {

    public CallbackType getType() {
        return CallbackType.BODY;
    }

    public abstract void exec(final String body) throws IOException;
}
