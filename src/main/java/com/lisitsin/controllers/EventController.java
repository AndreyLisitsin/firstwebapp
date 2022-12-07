package com.lisitsin.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lisitsin.model.Event;
import com.lisitsin.repository.Impl.EventRepositoryImpl;
import com.lisitsin.services.EventService;
import com.lisitsin.services.impl.EventServiceImpl;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/events/*")
public class EventController extends HttpServlet {

    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        eventService = new EventServiceImpl(new EventRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String[] contextPath = req.getPathInfo().split("/");
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        if (contextPath.length == 2) {
            Long id = Long.parseLong(contextPath[1]);
            Event event = eventService.getById(id);
            String eventJson = gson.toJson(event);
            pw.println(eventJson);
        }
        else if (contextPath.length == 0) {
            String eventJson = gson.toJson(eventService.getAll());
            pw.println(eventJson);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String[] contextPath = req.getPathInfo().split("/");
        String eventFromJson = contextPath[2];
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Event event = gson.fromJson(eventFromJson, Event.class);
        eventService.update(event);

        String eventToJson = gson.toJson(event);
        pw.println(eventToJson);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String[] contextPath = req.getPathInfo().split("/");
        String eventFromJson = contextPath[2];
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Event event = gson.fromJson(eventFromJson, Event.class);
        eventService.save(event);

        String eventToJson = gson.toJson(event);
        pw.println(eventToJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getPathInfo().split("/")[2]);
        eventService.remove(id);
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
