package com.senla.helloworld;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        try(PrintWriter printWriter = resp.getWriter()) {
            printWriter.println("Hello world");
        } catch (IOException e) {
            throw new BusinessException("Error request");
        }
    }
}