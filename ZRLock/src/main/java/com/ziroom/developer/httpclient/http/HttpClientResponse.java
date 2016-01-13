package com.ziroom.developer.httpclient.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.ziroom.developer.httpclient.http.callback.CallbackAsBody;
import com.ziroom.developer.httpclient.http.callback.CallbackAsResponse;
import com.ziroom.developer.httpclient.http.callback.CallbackAsStatusCode;
import com.ziroom.developer.httpclient.http.callback.HttpClientCallback;
import com.ziroom.developer.json.JsonJacksonUtil;

/**
 * 
 * HttpClientResponse
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:19:19
 * @since 1.0.0
 */
public class HttpClientResponse {

    protected CloseableHttpResponse response;
    protected String bodyAsString;
    protected List<HttpClientCallback> callbacks;

    protected HttpClientResponse(CloseableHttpResponse response, List<HttpClientCallback> callbacks) throws IOException {
        this.response = response;
        this.callbacks = callbacks;
        if (null != this.callbacks && !this.callbacks.isEmpty()) {
            doCallback();
        }
    }

    protected void doCallback() throws IOException {
        for (HttpClientCallback callback : this.callbacks) {
            switch (callback.getType()) {
                case RESPONSE:
                    ((CallbackAsResponse) callback).exec(this);
                    break;
                case BODY:
                    ((CallbackAsBody) callback).exec(getBody());
                    break;
                case STATUS_CODE:
                    ((CallbackAsStatusCode) callback).exec(status());
                    break;
                default:
                    throw new RuntimeException("not found callback type");
            }
        }
    }

    public int status() {
        return this.response.getStatusLine().getStatusCode();
    }

    public String getResponseHeader(final String key) {
        Header header = this.response.getLastHeader(key);
        return null != header ? header.getValue() : null;
    }

    public Header[] getResponseHeaders() {
        return this.response.getAllHeaders();
    }

    public String getBody() throws IOException {
        if (null == bodyAsString) {
            try {
                HttpEntity entity = response.getEntity();
                bodyAsString = EntityUtils.toString(entity, HttpClient.DEFAULT_CHARSET);
            } finally {
                this.response.close();
            }
        }
        return bodyAsString;
    }

    public String result() throws IOException {
        return getBody();
    }

    public <T> T result(final Class<T> clazz) throws IOException {
        if (status() < 400) {
        	return JsonJacksonUtil.toObject(result(), clazz);
        } else {
            throw new RuntimeException(this.response.getStatusLine().toString());
        }
    }
}
