package com.lisitsin.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lisitsin.model.User;
import com.lisitsin.repository.Impl.UserRepositoryImpl;
import com.lisitsin.services.UserService;
import com.lisitsin.services.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/users/*")
public class UserController extends HttpServlet {

    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserServiceImpl(new UserRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String[] contextPath = req.getPathInfo().split("/");
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        if (contextPath.length == 2) {
            Long id = Long.parseLong(contextPath[1]);
            User user = userService.getById(id);
            String userGson = gson.toJson(user);
            pw.println(userGson);
        }
        else if (contextPath.length == 0) {
            String usersGson = gson.toJson(userService.getAll());
            pw.println(usersGson);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] contextPath = req.getPathInfo().split("/");
        String userFromJson = contextPath[2];
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        User user = gson.fromJson(userFromJson, User.class);
        Long id = user.getId();
        userService.update(user);

        String userToJson = gson.toJson(user);
        PrintWriter pw = resp.getWriter();
        pw.println(userToJson);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] contextPath = req.getPathInfo().split("/");
        String userFromJson = contextPath[2];
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        User user = gson.fromJson(userFromJson, User.class);
        userService.save(user);

        String gsonUser = gson.toJson(user);
        PrintWriter pw = resp.getWriter();
        pw.println(gsonUser);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getPathInfo().split("/")[2]);
        userService.remove(id);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] contextPath = req.getPathInfo().split("/");
        String methodName = null;
        if (contextPath.length ==0 ){
            doGet(req, resp);
        }
        if (contextPath.length == 2 || contextPath.length == 3) {
            try {
                Long.parseLong(contextPath[1]);
                doGet(req, resp);
            } catch (NumberFormatException e) {}
            methodName = contextPath[1];
        }
        if ("create".equals(methodName)){
            doPut(req, resp);
        }
        if ("update".equals(methodName)){
            doPost(req, resp);
        }
        if ("delete".equals(methodName)){
            doDelete(req, resp);
        }
    }
}
