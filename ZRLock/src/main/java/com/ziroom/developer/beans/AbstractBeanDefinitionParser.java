package com.ziroom.developer.beans;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * 
 * Bean处理工具类
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午3:55:24
 * @since 1.0.0
 */
public abstract class AbstractBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    protected void parseChildElement(Element element, ParserContext parserContext, BeanDefinitionBuilder builder, String... childNames) {
        Element child;
        for (String childName : childNames) {
            child = DomUtils.getChildElementByTagName(element, childName);
            if (null != child) {
                builder.addPropertyValue(childName, parserContext.getDelegate().parseCustomElement(child, builder.getBeanDefinition()));
            }
        }
    }

    protected boolean shouldGenerateId() {
        return true;
    }

    protected abstract Class<?> getBeanClass(Element element);
}
