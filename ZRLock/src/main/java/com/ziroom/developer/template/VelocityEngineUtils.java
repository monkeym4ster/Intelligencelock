package com.ziroom.developer.template;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 *
 * Date: 14/10/23
 * Time: 20:29
 *
 * @author it_javasun@yeah.net
 */
public class VelocityEngineUtils {

    private final static String DEFAULT_CHARSET = "UTF-8";

    private final static VelocityEngine engine;

    static {
        engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        try {
            engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
                    new File(VelocityEngineUtils.class.getResource("/").toURI().getSchemeSpecificPart()).getAbsolutePath());
        } catch (URISyntaxException e) {
        }
    }

    @SuppressWarnings("rawtypes")
	public static void mergeTemplate(String templateLocation, String encoding, Map model,
                                     Writer writer) {
        mergeTemplate(engine, templateLocation, encoding, model, writer);
    }

    @SuppressWarnings("rawtypes")
	public static void mergeTemplate(VelocityEngine engine, String templateLocation, String encoding,
                                     Map model, Writer writer) {
        try {
            VelocityContext velocityContext = new VelocityContext(model);
            engine.mergeTemplate(templateLocation, encoding, velocityContext, writer);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new VelocityException(ex.toString());
        }
    }

    @SuppressWarnings("rawtypes")
	public static String mergeTemplateIntoString(String templateLocation, Map model) {
        return mergeTemplateIntoString(engine, templateLocation, model);
    }

    @SuppressWarnings("rawtypes")
	public static String mergeTemplateIntoString(VelocityEngine engine, String templateLocation,
                                                 Map model) {
        StringWriter result = new StringWriter();
        mergeTemplate(engine, templateLocation, DEFAULT_CHARSET, model, result);
        return result.toString();
    }
}
