package by.epam.rentacar.controller;


import by.epam.rentacar.controller.command.Command;
import by.epam.rentacar.controller.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("controller context path" + request.getContextPath());
        String commandStr = request.getParameter("command");
        Command command = CommandContainer.get(commandStr);
        command.execute(request, response);
    }
}
