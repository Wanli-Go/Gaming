package com.wanli.webapp.servlets;

import com.wanli.webapp.dao.GameDAO;
import com.wanli.webapp.dao.Impl.GameDAOImpl;
import com.wanli.webapp.entities.Game;
import com.wanli.webapp.utilities.ContextLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class GameImageUploadServlet extends HttpServlet {

    private GameDAO gameDAO = new GameDAOImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //System.out.println("文件上传...");
        //设置请求的编码格式
        req.setCharacterEncoding("UTF-8");
        //获取普通表单项（获取参数）
        String gid = req.getParameter("uid"); //表单中表单元素的name属性值
        //System.out.println("uid: " + uid);
        //获取Part对象 （Servlet 将 mutipart/form-data 的 POST 请求封装成 Part对象）
        Part part = req.getPart("myfile");
        //通过Part对象得到上传的文件名
        String fileName = part.getSubmittedFileName();
        //System.out.println("上传文件名：" + fileName);
        //得到文件存放的路径
        String filePath = req.getServletContext().getRealPath("/") + "/img";
        //System.out.println("文件存放路径：" + filePath);
        //上传文件到指定目录
        part.write(filePath + "/" + gid + ".jpg");
        res.getWriter().write("success!");
        System.out.print(ContextLogger.log(req) + "文件上传成功：" + gid + ".jpg；");
        Game game = gameDAO.getGame(Integer.parseInt(gid));
        game.setImage(gid + ".jpg");
        gameDAO.updateGame(game);
        System.out.println("游戏图片数据修改成功");
    }
}

