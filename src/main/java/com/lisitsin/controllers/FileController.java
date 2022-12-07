package com.lisitsin.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lisitsin.model.MyFile;
import com.lisitsin.repository.Impl.FileRepositoryImpl;
import com.lisitsin.services.FileService;
import com.lisitsin.services.impl.FileServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@WebServlet("/files/*")
public class FileController extends HttpServlet {

     private FileService fileService;

    private ServletFileUpload uploader = null;
    @Override
    public void init() throws ServletException{
        fileService = new FileServiceImpl(new FileRepositoryImpl());
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        java.io.File filesDir = (java.io.File) getServletContext().getAttribute("FILES_DIR_FILE");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String[] contextPath = req.getPathInfo().split("/");
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        if (contextPath.length == 2) {
            Long id = Long.parseLong(contextPath[1]);
            MyFile myFile = fileService.getById(id);
            String fileJson = gson.toJson(myFile);
            pw.println(fileJson);
        }
        else if (contextPath.length == 0) {
            List<MyFile> fileList = fileService.getAll();
            String fileJson = gson.toJson(fileList);
            pw.println(fileJson);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp, String filename) throws ServletException, IOException {
        MyFile myFile = fileService.getAll().stream().filter(f -> f.getName().equals(filename)).findFirst().orElse(null);
        InputStream fis = Files.newInputStream(Paths.get(myFile.getFilePath()));
            ServletOutputStream os = resp.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read = 0;
            while((read = fis.read(bufferData))!= -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] contextPath = req.getPathInfo().split("/");
        String eventFromJson = contextPath[2];
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        MyFile myFile = gson.fromJson(eventFromJson, MyFile.class);
        fileService.update(myFile);

        String fileToJson = gson.toJson(myFile);
        PrintWriter pw = resp.getWriter();
        pw.println(fileToJson);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String[] contextPath = req.getPathInfo().split("/");
        String dataForFileFromJson = contextPath[2];
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        MyFile myFileFromJson = gson.fromJson(dataForFileFromJson, MyFile.class);
        myFileFromJson.setUrlPath("localhost:8088/files/preview/"+ myFileFromJson.getName());

        try {
            List<FileItem> fileItemsList = uploader.parseRequest(req);
            Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
            while(fileItemsIterator.hasNext()){
                FileItem fileItem = fileItemsIterator.next();
                File file = new File(req.getServletContext().getAttribute("FILES_DIR")+ File.separator+fileItem.getName());
                myFileFromJson.setFilePath(file.getAbsolutePath());
                fileService.save(myFileFromJson);
                fileItem.write(file);
            }
        } catch (FileUploadException e) {
            pw.write("Exception in uploading file.");
        } catch (Exception e) {
            pw.write("Exception in uploading file.");
        }

        String fileToJson = gson.toJson(myFileFromJson);
        pw.println(fileToJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getPathInfo().split("/")[2]);
        fileService.remove(id);
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
        if ("preview".equals(methodName)) {
            doGet(req, resp, contextPath[2]);
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
