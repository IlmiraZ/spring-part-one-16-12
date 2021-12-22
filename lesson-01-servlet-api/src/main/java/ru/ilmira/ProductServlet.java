package ru.ilmira;

import ru.ilmira.persist.Product;
import ru.ilmira.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        if (req.getParameter("id") != null) {
            wr.println("<dl>");
            wr.println("<dt>Идентификатор продукта:</dt>");
            wr.println("<dd>" + req.getParameter("id") + "</dd>");
            wr.println("<dt>Наименование продукта:</dt>");
            wr.println("<dd>" + req.getParameter("name") + "</dd>");
            wr.println("</dl>");
            wr.println("<br>");
            wr.println("<a href = \"product\">Назад к списку продуктов</a>");
        } else if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            wr.println("<table>");

            wr.println("<tr>");
            wr.println("<th>Id</th>");
            wr.println("<th>Name</th>");
            wr.println("</tr>");

            for (Product product : productRepository.findAll()) {
                wr.println("<tr>");
                wr.println("<td>" + product.getId() + "</td>");
                wr.println("<td> <a href=\"product?id=" + product.getId() + "&name=" + product.getName() + "\">" + product.getName() + "</a></td>");
                wr.println("</tr>");
            }
            wr.println("</table>");
        }
    }
}
