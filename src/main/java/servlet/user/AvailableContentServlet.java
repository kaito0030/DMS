package servlet.user;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DocumentDAO;
import dto.DocumentDTO;
import dto.UserDTO;

@WebServlet("/available-content")
public class AvailableContentServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO loginUser =
                (UserDTO) session.getAttribute("loginUser");

        DocumentDAO documentDAO = new DocumentDAO();

        List<DocumentDTO> documentList =
                documentDAO.findAvailableByUserName(
                        loginUser.getUserName()
                );

        request.setAttribute("documentList", documentList);
        request.setAttribute(
                "contentPage",
                "../user/availableContent.jsp"
        );

        request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
               .forward(request, response);
    }
}