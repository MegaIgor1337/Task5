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
import java.util.ArrayList;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final Service Service = new Service();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            resp.setContentType("text/html");
            writer.println("<br/><a href=\"index.html\">Get authenticated</a>\n");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            resp.setContentType("text/html");
            String name = req.getParameter("name");
            String password = req.getParameter("pwd");
            String email = req.getParameter("email");
            int age = Integer.parseInt(req.getParameter("age"));
            UserDto user = Service.findUser(name);
            if (user != null) {
                writer.println("<h1>A user with the same name already exists</h1>");
                writer.println("<br/><a href=\"registration.html\">Try again</a>\n");
            } else {
                if (Service.validatePassword(password)) {
                    if (age < 0) {
                        writer.println("<h1>Age cannot be negative</h1>");
                        writer.println("<br/><a href=\"registration.html\">Try again</a>\n");
                    } else {
                        HttpSession session = req.getSession();
                        session.setAttribute("user", new UserDto(Service.findLastId() + 1,
                                name, password, email, age, new ArrayList<>()));
                        Service.addNewUser(name, password, email, age);
                        writer.println("<h1>Registration completed successfully</h1>");
                        writer.println("<br/><a href=\"menu.html\">User menu</a>\n");
                    }
                } else {
                    writer.println("<h1>Password is too simple</h1>");
                    writer.println("<h1>Password must contain at least 8 characters, " +
                            "one uppercase character, one number<h1>");
                    writer.println("<br/><a href=\"registration.html\">Try again</a>\n");
                }
            }
        }
    }
}
