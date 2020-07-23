//package com.itechart.contacts.web.controller;
//
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet(urlPatterns = "/*")
//public class FrontController extends HttpServlet {
//
//    private static final long serialVersionUID = -5314373917826193280L;
//    private final static Logger LOGGER = LogManager.getLogger();
//
//    @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String page = "/contacts";
//        System.out.println("хсссс");
//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//        try {
//            dispatcher.forward(request, response);
//        } catch (IOException e) {
//            LOGGER.log(Level.ERROR, "Requested page not found.");
//            response.sendError(404);
//        } catch (ServletException e) {
//            LOGGER.log(Level.ERROR, "Processing request failed.");
//            response.sendError(500);
//        }
//    }
//
//}
