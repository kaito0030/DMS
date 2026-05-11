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

        UserDAO userDAO = new UserDAO();

        List<UserDTO> userList = userDAO.findAll();

        request.setAttribute("userList", userList);
        request.setAttribute(
                "contentPage",
                "../user/userList.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }
}