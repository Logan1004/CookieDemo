<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%

  //el表达式
  String names="";
  String pwd="";
  int p=0;
//取出Cookie
  Cookie [] cookie=request.getCookies();
  for (int i=0;i<cookie.length;i++){
    if(cookie[i].getName().equals("users")){
      //存着数据（用户名+密码）
      names=cookie[i].getValue().split("-")[0];
      pwd=cookie[i].getValue().split("-")[1];

      //再一次的存起来（备用）
      request.setAttribute("xingming",names);
      request.setAttribute("mima", pwd);
    }
  }

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">

  <title>My JSP 'index.jsp' starting page</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->
</head>

<body>
<form action="/Servlet.CookieServlet" method="post">
  用户名:<input type="text" name="uname" id="uname" value="${xingming}"/><br>
  密码:<input type="text" name="password" id="password" value="${mima}"/><br>
  <input type="checkbox" name="ck">记住用户名和密码<br>
  <input type="submit" value="登录">
</form>


<form action="/Servlet.CookieServlet" method="GET">
  <input type="submit" value="ShowAll" />
</form>

</body>
</html>