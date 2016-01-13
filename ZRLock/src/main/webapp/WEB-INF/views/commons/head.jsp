<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//本系统
	request.setAttribute("webapp", path);
%>

<!-- The fav icon -->
<link rel="shortcut icon" href="${webapp}/img/favicon/favicon.png">

<script type="text/javascript">
	window.webapp = "${webapp}";
</script>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

