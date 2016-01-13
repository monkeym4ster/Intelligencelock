package com.ziroom.developer.httpclient.http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.ziroom.developer.httpclient.http.security.AESEncoder;
import com.ziroom.developer.httpclient.http.security.CryptoType;
import com.ziroom.developer.json.JsonUtil;

/**
 * HttpClientPost
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:18:25
 * @since 1.0.0
 */
public class HttpClientPost extends HttpClient {

    protected Map<String, ContentBody> multipartParameters;

    protected HttpClientPost(final HttpMethod method, final String url) {
        super(method, url);
    }

    protected HttpClientPost(final HttpMethod method, final String url, final String charset) {
        super(method, url, charset);
    }

    public HttpClientPost multipart() {
        MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();
        if (parameters != null && !parameters.isEmpty()) {
            for (NameValuePair pair : parameters) {
                multipartBuilder.addPart(pair.getName(), new StringBody(pair.getValue(), ContentType.TEXT_PLAIN));
            }
        }
        if (multipartParameters != null && !multipartParameters.isEmpty()) {
            for (Map.Entry<String, ContentBody> entry : multipartParameters.entrySet()) {
                multipartBuilder.addPart(entry.getKey(), entry.getValue());
            }
        }
        this.requestBuilder.setEntity(multipartBuilder.build());
        return this;
    }

    public HttpClientPost json(final String body) {
        StringEntity entity = new StringEntity(body, this.requestBuilder.getCharset());
        entity.setContentType(ContentType.create(ContentType.APPLICATION_JSON.getMimeType(), this.charset).toString());
        this.requestBuilder.setEntity(entity);
        return this;
    }

    public HttpClientPost json(final String body, final String key, final CryptoType type)
            throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        this.setHeader(HEADER_CONTENT_CRYPTO_TYPE, type.toString());
        return json(AESEncoder.encrypt(body, key));
    }

    public HttpClientPost json(final String body, final String key)
            throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return json(body, key, CryptoType.AES);
    }

    public <T> HttpClientPost json(final T body) throws IOException {
        return json(JsonUtil.toJacksonJson(body));
    }

    public <T> HttpClientPost json(final T body, final String key)
            throws IOException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return json(JsonUtil.toJacksonJson(body), key);
    }

    public <T> HttpClientPost form(final T body) {
        Field[] fields = body.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                this.addParameter(field.getName(), String.valueOf(field.get(body)));
            } catch (IllegalAccessException e) {
            }
        }
        return this;
    }

    public HttpClientPost addParameter(final String name, final String value) {
        super.addParameter(name, value);
        return this;
    }

    public HttpClientPost addParameter(final String name, final File value) {
        if (multipartParameters == null) {
            multipartParameters = new HashMap<String, ContentBody>();
        }
        multipartParameters.put(name, new FileBody(value));
        return this;
    }
}
