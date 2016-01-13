package com.ziroom.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ziroom.common.config.GetProperties;


/**
 * 
 * 登录过滤器
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年9月1日 下午11:53:14
 * @since 1.0.0
 */
public class LoginFilter implements Filter {
	private static final String SHOW_LOGIN_PATH = "SHOW_LOGIN_PATH";	//显示登录页面
	private static final String DO_LOGIN_PATH = "DO_LOGIN_PATH";		//登录操作不能过滤掉
	private static final String LOGIN_PERSONID = "LOGIN_PERSONID";		//登录用户在session中的属性key -- session.setAttribute(key,value)
	private String showloginPath;
	private String dologinPath;
	private String loginPersonId;
	
    public void init(FilterConfig config) throws ServletException {
    	showloginPath = config.getInitParameter(SHOW_LOGIN_PATH);
    	dologinPath = config.getInitParameter(DO_LOGIN_PATH);
    	loginPersonId = config.getInitParameter(LOGIN_PERSONID);
    	if(showloginPath==null || showloginPath.equals("") || showloginPath.equals("null") ){
    		throw new ServletException("登录页面配置出错...");
    	}
    }
    
    /**
     * 
     * 登录过滤
     * <p><b>注意：</b><br>
     * </p>
     * <p>
     * <b>变更记录：</b><br>
     * </p>
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @author: 张志宏 
     * @email:  it_javasun@yeah.net
     * @date:2015年9月1日 下午11:54:19
     * @since 1.0.0
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	 HttpServletRequest  httpReq  = (HttpServletRequest) request;
         HttpServletResponse httpResp = (HttpServletResponse) response;
         
         // 文件路径
         String fileUrlSession = (String) httpReq.getSession().getAttribute("fileUrl");
         if (StringUtils.isBlank(fileUrlSession)) {
        	 String fileUrl = GetProperties.getValue("fileUrl");
        	 httpReq.getSession().setAttribute("fileUrl", fileUrl);
         }
         
         
         httpResp.setHeader("Content-type","text/html;charset=UTF-8");
         httpResp.setContentType("text/html");
         //判断是否是登陆页面
         String servletPath = httpReq.getServletPath();
         
         // 判断传入的路径是否为空
         if (StringUtils.isNotBlank(servletPath)) {
        	 // 静态资源过滤
        	 boolean staticFlag = staticResourceFilter(servletPath);
        	 if (staticFlag) {
        		 chain.doFilter(request, response);
            	 return;
        	 }
        	 
        	 // 请求接口过滤
        	 String requestType = httpReq.getParameter("request_type");
        	 String timeStamp = httpReq.getParameter("time_stamp");
        	 if (timeStamp != null && StringUtils.isNotBlank(timeStamp)
        			 && requestType != null && StringUtils.equals(requestType, "I")
        			 ) {
        		 chain.doFilter(request, response);
            	 return;
        	 }
         }
         
         
         //flag:若为登陆页面的action路径 showloginPath/nologinPath,则赋值true，否则赋值false
         boolean flag = false;
         if(servletPath.equals(showloginPath) 
        		 || servletPath.equals(dologinPath)){
        	 chain.doFilter(request, response);
        	 flag = true;
        	 return;
         }
         else
         {   //如果不是登录页面，则需先判断用户是否已登录
        	 Object loginId = httpReq.getSession().getAttribute(loginPersonId);
        	 if(loginId != null){//如果不为空,则进行已登录处理
        		 chain.doFilter(request, response);
        	 } else {//如果为空,则进行未登录处理
        		 if ( httpReq.getQueryString() != null )
            	 {
            		 servletPath += "?"+httpReq.getQueryString();
            	 }
            	 httpReq.getSession().setAttribute("returnUri", servletPath);
                 if ( flag == false )
                 {	
                	// _top适用于framesets
                	String contextPath = httpReq.getContextPath()+showloginPath;
                	java.io.PrintWriter out = response.getWriter();  
            	    out.println("<html>");  
            	    out.println("<script>");  
            	    out.println("window.open ('"+contextPath+"','_top')");  
            	    out.println("</script>");  
            	    out.println("</html>"); 
                	 
                	 // httpResp.sendRedirect(contextPath);
                	 // 跳转到登录界面
                	 // httpReq.getRequestDispatcher(showloginPath).forward(httpReq,httpResp);
                 } 
        	 }
         }
    }
    
    public void destroy(){
    	//do something
    }
    
    /**
     * 
     * 静态资源过滤器
     * @param servletPath
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年9月2日 上午10:29:46
     * @since 1.0.0
     */
    private boolean staticResourceFilter(String servletPath) {
    	
    	if(servletPath.startsWith("/js")){
    		return true;
    	} else if (servletPath.startsWith("/editor")) {
    		return true;
    	} else if (servletPath.startsWith("/css")) {
    		return true;
    	} else if (servletPath.startsWith("/images")) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
}