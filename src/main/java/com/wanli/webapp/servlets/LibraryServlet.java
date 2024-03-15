package com.wanli.webapp.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wanli.webapp.dao.GameDAO;
import com.wanli.webapp.dao.Impl.GameDAOImpl;
import com.wanli.webapp.entities.Game;
import com.wanli.webapp.entities.User;
import com.wanli.webapp.utilities.ContextLogger;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/library")
public class LibraryServlet extends HttpServlet {
    private GameDAO gameDAO = new GameDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Cookie[] cookies = request.getCookies();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonString = URLDecoder.decode(cookies[0].getValue(), StandardCharsets.UTF_8.toString());
        //System.out.println(jsonString);
        User user = gson.fromJson(jsonString, User.class);
        response.setContentType("application/json;charset=UTF-8");
        List<Game> games = gameDAO.getUserGames(user.getUid());
        String gamesJsonString = gson.toJson(games);
        System.out.println(ContextLogger.log(request) + "返回用户游戏");
        response.getWriter().write(gamesJsonString);
    }
}
