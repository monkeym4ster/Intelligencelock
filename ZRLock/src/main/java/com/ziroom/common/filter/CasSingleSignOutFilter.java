package com.ziroom.common.filter ;

import java.io.IOException ;

import javax.servlet.Filter ;
import javax.servlet.FilterChain ;
import javax.servlet.FilterConfig ;
import javax.servlet.ServletException ;
import javax.servlet.ServletRequest ;
import javax.servlet.ServletResponse ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;

import org.apache.commons.lang.StringUtils ;
import org.jasig.cas.client.util.XmlUtils ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import com.ziroom.common.util.RedisClient;



/**
 * CAS集群单点登出过滤器
 * 
 * @author 张洪朋
 * @version 1.0 2014-5-4
 */
public class CasSingleSignOutFilter implements Filter {

	// ticket参数名
	private final static String ARTIFACT_PARAMETER_NAME = "ticket" ;

	// 登出参数名
	private final static String LOGOUT_PARAMETER_NAME = "logoutRequest" ;

	// sessionID登出后缀
	private final static String LOGOUT_SUFFIX = "_isLogout" ;

	private static final Logger LOGGER = LoggerFactory.getLogger(CasSingleSignOutFilter.class) ;

	/**
	 * 主要逻辑代码 记录首次登入的session,登出时标记,再次访问时验证是否已登出 如登出则作废当前session
	 */
	public void doFilter(ServletRequest servletRequest , ServletResponse servletResponse , FilterChain filterChain) throws IOException , ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse ;
		HttpServletRequest request = (HttpServletRequest) servletRequest ;

		if (isTokenRequest(request)) {
			// 首次登入时记录session
			recordSession(request) ;
		} else
			if (isLogoutRequest(request)) {
				// 登出时将session做上标记
				destroySession(request) ;
				return ;
			} else {
				// 如果已登出则销毁session
				validSession(request) ;
			}

		filterChain.doFilter(request , response) ;
	}

	/**
	 * 验证session是否已登出
	 * 
	 * @param request
	 */
	public void validSession(final HttpServletRequest request) {
		final HttpSession session = request.getSession(true) ;
		String sessionId = session.getId() ;
		// 从缓存中获取session是否登出
		String isLogoutSession = (String)RedisClient.getInstance().get(sessionId + LOGOUT_SUFFIX) ;
		if (StringUtils.isNotBlank(isLogoutSession)) {
			//调试redis异常
			LOGGER.info("用户登出销毁session请求，sessionId:"+sessionId);
			Boolean isLogout = Boolean.valueOf(isLogoutSession) ;
			if (isLogout != null && isLogout) {
				// 已登出,销毁session
				session.invalidate() ;
				RedisClient.getInstance().delete(sessionId + LOGOUT_SUFFIX) ;
			}
		}

	}

	/**
	 * 标记session已登出
	 * 
	 * @param request
	 */
	public void destroySession(final HttpServletRequest request) {

		// 接收CAS server登出参数
		final String logoutMessage = safeGetParameter(request , LOGOUT_PARAMETER_NAME) ;
		LOGGER.debug("Logout request:\n" + logoutMessage) ;

		// ticket信息
		final String token = XmlUtils.getTextForElement(logoutMessage , "SessionIndex") ;

		if (StringUtils.isNotBlank(token)) {

			String sessionId = (String) RedisClient.getInstance().get(token) ;
			// 删除首次登入时的记录
			RedisClient.getInstance().delete(token) ;
			// 标记当前session已登出
			RedisClient.getInstance().set(sessionId + LOGOUT_SUFFIX , "true", 0) ;
		}
	}

	/**
	 * 首次登入时记录ticket与session的映射关系
	 * 
	 * @param request
	 */
	private void recordSession(final HttpServletRequest request) {
		final String token = safeGetParameter(request , ARTIFACT_PARAMETER_NAME) ;
		final HttpSession session = request.getSession(true) ;
		RedisClient.getInstance().set(token , session.getId(), 0) ;
	}

	/**
	 * cas客户端源码所带逻辑,用途未知
	 * 
	 * @param request
	 * @return
	 */
	private boolean isMultipartRequest(final HttpServletRequest request) {
		return request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart") ;
	}

	/**
	 * 判断是否为登出请求
	 * 
	 * @param request
	 * @return
	 */
	public boolean isLogoutRequest(final HttpServletRequest request) {
		return "POST".equals(request.getMethod()) && !isMultipartRequest(request) && StringUtils.isNotBlank(safeGetParameter(request , LOGOUT_PARAMETER_NAME)) ;
	}

	/**
	 * 判断是否为首次登入请求
	 * 
	 * @param request
	 * @return
	 */
	public boolean isTokenRequest(final HttpServletRequest request) {
		return StringUtils.isNotBlank(safeGetParameter(request , ARTIFACT_PARAMETER_NAME)) ;
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @param parameter
	 * @return
	 */
	public String safeGetParameter(final HttpServletRequest request , final String parameter) {
		if ("POST".equals(request.getMethod()) && LOGOUT_PARAMETER_NAME.equals(parameter)) {
			LOGGER.debug("safeGetParameter called on a POST HttpServletRequest for LogoutRequest.  Cannot complete check safely.  Reverting to standard behavior for this Parameter") ;
			return request.getParameter(parameter) ;
		}
		return request.getQueryString() == null || request.getQueryString().indexOf(parameter) == -1 ? null : request.getParameter(parameter) ;
	}

	public void destroy() {

	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
