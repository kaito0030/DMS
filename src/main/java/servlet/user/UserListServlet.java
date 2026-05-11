package servlet.user;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dto.UserDTO;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String searchColumn = request.getParameter("searchColumn");
        String keyword = request.getParameter("keyword");

        UserDAO userDAO = new UserDAO();

        List<UserDTO> userList;

        if (searchColumn == null || keyword == null || keyword.isBlank()) {
            userList = userDAO.findAll();
        } else {
            userList = userDAO.search(searchColumn, keyword);
        }

        request.setAttribute("userList", userList);
        request.setAttribute("searchColumn", searchColumn);
        request.setAttribute("keyword", keyword);

        request.setAttribute(
                "contentPage",
                "../user/userList.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }
}