package com.wanli.webapp.servlets;

import com.google.gson.Gson;
import com.wanli.webapp.dao.GameDAO;
import com.wanli.webapp.dao.Impl.GameDAOImpl;
import com.wanli.webapp.utilities.ContextLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


@WebServlet("/delete")
public class GameDeletionServlet extends HttpServlet {
    private GameDAO gameDAO = new GameDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        int[] gameIds = gson.fromJson(reader, int[].class);
        for (int id : gameIds) {
            gameDAO.deleteGame(id);

        }

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println(ContextLogger.log(request) + "成功删除游戏（ids: " + Arrays.toString(gameIds) + "）");
        out.flush();
    }
}
