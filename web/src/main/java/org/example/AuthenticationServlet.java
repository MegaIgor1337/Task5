package org.example;

import example.userDto.UserDto;
import example.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {
    private final Service Service = new Service();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (var writer = resp.getWriter()) {
            String name = req.getParameter("name");
            String pwd = req.getParameter("pwd");
            UserDto user = Service.findUser(name, pwd);
            if (user != null) {
                writer.println("<h1> You logged in successfully </h1>");
                writer.println("<br/><a href=\"menu.html\">User menu</a>\n");
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
            } else {
                writer.println("<h1> Data entered incorrectly </h1>");
                writer.println("<br/><a href=\"index.html\">Try again</a>\n");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (var writer = resp.getWriter()) {
            writer.println("<br/><a href=\"index.html\">Authentication</a>\n");
        }
    }
}
