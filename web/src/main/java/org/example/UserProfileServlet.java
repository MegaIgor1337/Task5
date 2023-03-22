package org.example;

import example.userDto.UserDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class UserProfileServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            resp.setContentType("text/html");
            HttpSession session = req.getSession();
            UserDto user = (UserDto) session.getAttribute("user");
            writer.println(
                    "Name: " + user.getName() + "</br>" +
                            "Password: " + user.getPassword() + "</br>" +
                            "Email: " + user.getEmail() + "</br>" +
                            "Age: " + user.getAge() + "</br>" +
                            "Products:" + user.getProducts() + "</h1>"

            );
            req.setAttribute("user", user);
            writer.println("<br/><a href=\"menu.html\">Back to menu</a>");
        }
    }
}
