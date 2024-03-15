package com.wanli.webapp.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wanli.webapp.dao.Impl.GameDAOImpl;
import com.wanli.webapp.dao.Impl.UserDAOImpl;
import com.wanli.webapp.entities.Game;
import com.wanli.webapp.entities.User;
import com.wanli.webapp.utilities.ContextLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/create")
public class GameCreateServlet extends HttpServlet {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private GameDAOImpl gameDAO = new GameDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // Get a list of users that are publishers (utype = 2)
        List<User> publishers = userDAO.getAllUsers();
        publishers.removeIf(user -> user.getUtype() != 2);

        PrintWriter out = res.getWriter();
        System.out.println(ContextLogger.log(req) + "返回所有发行商数据...");

        out.print(gson.toJson(publishers));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        Game newGame = gson.fromJson(req.getReader(), Game.class);

        // Insert the new game into the database
        gameDAO.insertGame(newGame);

        // Return a success message
        res.getWriter().print(gson.toJson("Game created successfully."));

    }
}

