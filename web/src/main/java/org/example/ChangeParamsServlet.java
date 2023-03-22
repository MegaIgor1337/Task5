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

@WebServlet("/changeParams")
public class ChangeParamsServlet extends HttpServlet {
    private final Service Service = new Service();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (var writer = resp.getWriter()) {
            writer.println("<br/><a href=\"index.html\">Get authenticated</a>\n");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            resp.setContentType("text/html");
            HttpSession session = req.getSession();
            UserDto user = (UserDto) session.getAttribute("user");
            String param = req.getParameter("param");
            String value = req.getParameter("newValue");
            String value1 = req.getParameter("newValue");
            boolean check = true;
            if (param.equalsIgnoreCase("name")) {
                if (Service.validateName(value)) {
                    user.setName(value);
                } else {
                    check = false;
                    writer.println("<h1>This name already exists<h1>");
                    writer.println("<a href=\"paramsChange.html\">Try again</a>");
                }
            } else if (param.equalsIgnoreCase("password")) {
                if (Service.validatePassword(value)) {
                    user.setPassword(value);
                } else {
                    check = false;
                    writer.println("<h1>Password is too simple</h1>");
                    writer.println("<h1>Password must contain at least 8 characters, " +
                            "one uppercase character, one number<h1>");
                    writer.println("<a href=\"paramsChange.html\">Try again</a>");
                }
            } else if (param.equalsIgnoreCase("email")) {
                user.setEmail(value);
            } else if (param.equalsIgnoreCase("age")) {
                if (Integer.parseInt(param) < 0) {
                    check = false;
                    writer.println("<h1>Age cannot be negative");
                    writer.println("<a href=\"paramsChange.html\">Try again</a>");
                } else {
                    user.setAge(Integer.parseInt(value));
                }
            }
            if (check) {
                Service.replaceUser(user);
                writer.println("<h1>Parameter changed</h1>");
                writer.println("<br/><a href=\"menu.html\">Back to menu</a>");
            }
        }
    }
}
