<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@include file="../commons/head.jsp"%>
	<title>后台登陆页面</title> 
	
	<!-- Stylesheets -->
	<link rel="stylesheet" href="${webapp }/style/bootstrap.css"/>
	<link rel="stylesheet" href="${webapp }/style/font-awesome.css"/>
	<link rel="stylesheet" href="${webapp }/style/style.css"/>
	
	<script src="${webapp }/js/html5shim.js"></script>
</head>

<body>

<!-- Form area -->
<div class="admin-form">
  <div class="container">

    <div class="row">
      <div class="col-md-12">
        <!-- Widget starts -->
            <div class="widget worange">
              <!-- Widget head -->
              <div class="widget-head">
                <i class="icon-lock"></i> 自如智能锁管理系统 
              </div>

              <div class="widget-content">
                <div class="padd">
                  <!-- Login form -->
                  <form class="form-horizontal" action="${webapp }/loginController/index" method="post">
                    <!-- Email -->
                    <div class="form-group">
                      <label class="control-label col-lg-3" for="inputEmail">用户名</label>
                      <div class="col-lg-9">
                        <input type="text" class="form-control" id="input_username" placeholder="请输入用户名">
                      </div>
                    </div>
                    <!-- Password -->
                    <div class="form-group">
                      <label class="control-label col-lg-3" for="inputPassword">密码</label>
                      <div class="col-lg-9">
                        <input type="password" class="form-control" id="input_password" placeholder="请输入密码">
                      </div>
                    </div>
                    <!-- Remember me checkbox and sign in button -->
                    <div class="form-group">
					<div class="col-lg-9 col-lg-offset-3">
					</div>
					</div>
                        <div class="col-lg-9 col-lg-offset-2">
							<button type="submit" class="btn btn-danger">登录</button>
						</div>
                    <br />
                  </form>
				  
				</div>
                </div>
              
                <div class="widget-foot">
                  <a href="#">自如员工登录入口</a>
                </div>
            </div>  
      </div>
    </div>
  </div> 
</div>

<!-- JS -->
<script src="${webapp }/js/jquery.js"></script>
<script src="${webapp }/js/bootstrap.js"></script>

</body>
</html>