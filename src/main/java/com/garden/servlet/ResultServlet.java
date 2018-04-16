package com.garden.servlet;

import com.garden.config.AppConfig;
import com.garden.service.BouquetService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ResultServlet extends HttpServlet {

    private BouquetService bouquetService;

    @Override
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        bouquetService = context.getBean(BouquetService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = bouquetService.getJsonString(Long.valueOf(req.getParameter("id")));
        req.setAttribute("bouquet", json);
        req.getRequestDispatcher("result.jsp").forward(req, resp);
    }

}
