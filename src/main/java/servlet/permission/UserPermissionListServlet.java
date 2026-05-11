package servlet.permission;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DocumentDAO;
import dto.DocumentDTO;

@WebServlet("/user-permission-list")
public class UserPermissionListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String userName =
                request.getParameter("userName");

        DocumentDAO documentDAO = new DocumentDAO();

        List<DocumentDTO> documentList =
                documentDAO.findAvailableByUserName(userName);

        request.setAttribute("targetUserName", userName);
        request.setAttribute("documentList", documentList);

        request.setAttribute(
                "contentPage",
                "../permission/userPermissionList.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }
}