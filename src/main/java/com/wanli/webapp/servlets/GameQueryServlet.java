package com.wanli.webapp.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wanli.webapp.dao.GameDAO;
import com.wanli.webapp.dao.Impl.GameDAOImpl;
import com.wanli.webapp.entities.Game;
import com.wanli.webapp.utilities.ContextLogger;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/games")
public class GameQueryServlet extends HttpServlet {
    private GameDAO gameDAO = new GameDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<Game> games = gameDAO.getAllGames();
        System.out.println(games.get(0).getRdate());
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String gamesJsonString = gson.toJson(games);
        System.out.println(ContextLogger.log(request) + "返回全部游戏数据");
        response.getWriter().write(gamesJsonString);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String query = request.getParameter("query");
        if (query != null) {
            response.setContentType("application/json;charset=UTF-8");
            List<Game> games = gameDAO.fuzzyQuery(query);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String gamesJsonString = gson.toJson(games);
            System.out.println(ContextLogger.log(request) + "执行模糊查询游戏数据，结果：" + gamesJsonString);
            response.getWriter().write(gamesJsonString);
        }
    }
}
