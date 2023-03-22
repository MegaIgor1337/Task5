package org.example;

import example.productDto.ProductDto;
import example.userDto.UserDto;
import example.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/buyProduct")
public class BuyProductsServlet extends HttpServlet {
    private final Service Service = new Service();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        ProductDto productDto = (ProductDto) session.getAttribute("product");
        try (var writer = resp.getWriter()) {
            if (req.getParameter("submit").equals("accept")) {
                Service.addProductToUser(user, productDto);
                session.setAttribute("user", user);
                writer.println("<h1>Product added to your list");
                writer.println("<br/><a href=\"menu.html\">Back to menu</a>");
            } else {
                writer.println("<a href=\"menu.html\">Back to menu</a>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (var writer = resp.getWriter()) {
            HttpSession session = req.getSession();
            UserDto user = (UserDto) session.getAttribute("user");
            String nameOfProduct = req.getParameter("nameOfProduct");
            String count = req.getParameter("count");
            ProductDto userProduct = Service.getProductForUser(nameOfProduct, Integer.parseInt(count));
            if (userProduct != null && Integer.parseInt(count) > 0) {
                session.setAttribute("product", userProduct);
                int price = Service.getPrice(nameOfProduct, Integer.parseInt(count));
                writer.println("<h1>Price " + price + "$</h1>");
                writer.println("<form action = \"buyProduct\" method = \"GET\">");
                writer.println("<button type=\"submit\" name=\"submit\" value=\"accept\">Accept</button>");
                writer.println("<button type=\"submit\" name=\"submit\" value=\"cancel\">Cancel</button>");
                writer.println("</form>");
            } else {
                writer.println("<h1>This item is out of stock</h1>");
                writer.println("<h1><a href=\"productBuy.html\">Try again</a></h1>");
            }
        }
    }
}
