<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/26/026
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>在线部署ShadowSocks</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<div class="load">
    <img src="<%=request.getContextPath()%>/img/loading_orange.gif" alt="">
</div>
<div class="container">
    <div class="form row">
        <div class="form-horizontal col-md-offset-3" id="login_form">
            <h3 class="form-title">在线部署ShadowSocks</h3>
            <div class="col-md-9">
                <div class="form-group">
                    IP&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp：
                    <i class="fa fa-user fa-lg"></i>
                    <input class="form-control required" type="text" placeholder="ip" id="ip" name="ip" autofocus="autofocus" maxlength="20"/>
                </div>
                <div class="form-group">
                    用户名：
                    <i class="fa fa-lock fa-lg"></i>
                    <input class="form-control required" type="text" placeholder="un" id="un" name="un" maxlength="8" />
                </div>
                <div class="form-group">
                    密码&nbsp&nbsp&nbsp&nbsp：
                    <i class="fa fa-lock fa-lg"></i>
                    <input class="form-control required" type="password" placeholder="Password" id="password" name="password" maxlength="8"/>
                </div>
                <div class="form-group">
                    密钥&nbsp&nbsp：&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp

                    <input class="form-group col-md-offset-9" type="file" placeholder="File" id="file" name="password" maxlength="8"/>
                </div>
                <%--<div class="form-group">--%>
                    <%--密码：--%>
                    <%--<label class="checkbox">--%>
                        <%--<input type="checkbox" name="remember" value="1"/>记住我--%>
                    <%--</label>--%>
                <%--</div>--%>
                <div class="form-group col-md-offset-9">
                    <button type="submit" class="btn btn-success pull-right" name="submit">一键部署</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.min.js"></script>

<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</html>