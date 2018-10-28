package Servlet;

import Entity.Constant;
import Entity.PersistentCookieStore;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieServlet extends HttpServlet {

    java.net.CookieStore cookieStore = new PersistentCookieStore();
    String ip = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String title = "Cookie 汇总";
        String docType = "<!DOCTYPE html>\n";
        InetAddress ip6 = InetAddress.getByName(ip);

        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip6);
        System.out.println(networkInterface.getDisplayName());

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n");

        List<HttpCookie> cookies = cookieStore.getCookies();
        for (HttpCookie cookie:cookies) {
            out.println(
                    "<ul>\n" +
                            "  <li><b>姓名</b>：" + cookie.getName() + "\n</li>" +
                            "  <li><b>密码</b>：" + cookie.getValue() + "\n</li>" +
                            "  <li><b>Display name:</b>：" + networkInterface.getDisplayName() + "\n</li>" +
                            "  <li><b>Name </b>：" + networkInterface.getName() + "\n</li>" +
                            "  <li><b>Up? </b>：" + networkInterface.isUp() + "\n</li>" +
                            "  <li><b>Loopback? </b>：" + networkInterface.isLoopback() + "\n</li>" +
                            "  <li><b>PointToPoint? </b>：" + networkInterface.isPointToPoint() + "\n</li>" +
                            "  <li><b>Supports multicast? </b>：" + networkInterface.supportsMulticast() + "\n</li>" +
                            "  <li><b>Hardware address </b>：" + networkInterface.getHardwareAddress() + "\n</li>" +
                            "  <li><b>MTU </b>：" +networkInterface.getMTU() + "\n</li>" +
                            "  <li><b>IPV6 </b>：" +ip6 + "\n</li>" +
                            "</ul>\n");
        }

        out.println(
                "</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String userName=URLEncoder.encode(request.getParameter("uname"), "UTF-8");
        String passWord=request.getParameter("password");

        String ck = "";
        if (request.getParameter("ck")!=null)
            ck=request.getParameter("ck");
        ip = request.getRemoteAddr();
        System.out.println(ip);
        //被选中的状态是on 没有被选中的状态下是null
        if("on".equals(ck)){
            //构造Cookie对象
            Cookie cookie=new Cookie("users", userName+"-"+passWord);
            HttpCookie httpCookie = new HttpCookie(userName,passWord);
            URI uri = URI.create("http://localhost:8888") ;
            cookieStore.add(uri,httpCookie);
            //PersistentCookieStore.store.add(url,cookie);

            //设置过期时间
            cookie.setMaxAge(600);
            //存储
            response.addCookie(cookie);
        }
        request.getRequestDispatcher("/Success.jsp").forward(request, response);
    }
}