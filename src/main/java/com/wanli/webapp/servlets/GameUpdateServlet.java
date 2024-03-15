package com.wanli.webapp.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wanli.webapp.dao.GameDAO;
import com.wanli.webapp.dao.Impl.GameDAOImpl;
import com.wanli.webapp.entities.Game;
import com.wanli.webapp.utilities.ContextLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/update")
public class GameUpdateServlet extends HttpServlet {
    private GameDAO gameDAO = new GameDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Game game = gson.fromJson(reader, Game.class);
        boolean updated = gameDAO.updateGame(game);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(updated ? "Game updated successfully" : "Error updating game"));
        System.out.println(ContextLogger.log(request) + "更新用户数据：" + game.getImage());
        out.flush();
    }
}
