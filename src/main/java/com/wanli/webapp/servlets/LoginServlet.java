package com.wanli.webapp.servlets;

import com.google.gson.Gson;
import com.wanli.webapp.dao.Impl.UserDAOImpl;
import com.wanli.webapp.dao.UserDAO;
import com.wanli.webapp.entities.User;
import com.wanli.webapp.utilities.ContextLogger;
import com.wanli.webapp.utilities.PasswordChecker;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(ContextLogger.log(request) + "处理登陆数据：" + username + "---" + password);
        //System.out.println(request.getServletContext().getRealPath("/"));
        //简单检错
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {response.getWriter().write("NG");}
        else {
            //获取数据
            UserDAO userDAO = new UserDAOImpl();
            User user = userDAO.getUserByUsername(username);

            //Thread.sleep
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //比较并进行输出
            if (PasswordChecker.check(username,password,user)) {
                response.getWriter().write("OK");
                Gson gson = new Gson();
                String jsonString = gson.toJson(user);
                //System.out.println("Serialized JSON: " + jsonString);
                String encodedJson = URLEncoder.encode(jsonString, StandardCharsets.UTF_8.toString());
                Cookie cookie = new Cookie("userData", encodedJson);
                cookie.setMaxAge(10*60);
                response.addCookie(cookie);
            } else {
                response.getWriter().write("NG");
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        try {
            Gson gson = new Gson();
            String jsonString = URLDecoder.decode(cookies[0].getValue(), StandardCharsets.UTF_8.toString());
            //System.out.println(jsonString);
            User user = gson.fromJson(jsonString, User.class);
            System.out.println(ContextLogger.log(request) + "返回登陆数据：" + user.getUname()+ "---" + user.getUid() + "---" + user.getUtype());
            response.getWriter().write(user.getUname()+ "---" + user.getUid() + "---" + user.getUtype());
        } catch (Exception e){
            System.out.println("An error has occurred with login values:");
            e.printStackTrace();
        }
    }
}