package servlet.permission;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DocumentDAO;
import dao.PermissionDAO;
import dao.UserDAO;
import dto.DocumentDTO;
import dto.UserDTO;

@WebServlet("/permission-register")
public class PermissionRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String documentId = request.getParameter("documentId");

        UserDAO userDAO = new UserDAO();
        DocumentDAO documentDAO = new DocumentDAO();

        List<UserDTO> userList = userDAO.findAll();
        List<DocumentDTO> documentList = documentDAO.findAll();

        request.setAttribute("userName", userName);
        request.setAttribute("documentId", documentId);
        request.setAttribute("userList", userList);
        request.setAttribute("documentList", documentList);

        request.setAttribute(
                "contentPage",
                "../permission/permissionRegister.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("userName");
        String documentId = request.getParameter("documentId");
        String permissionType = request.getParameter("permissionType");

        PermissionDAO permissionDAO = new PermissionDAO();

        if ("allow".equals(permissionType)) {

            permissionDAO.insertPermission(userName, documentId);

        } else if ("stop".equals(permissionType)) {

            permissionDAO.deletePermission(userName, documentId);
        }

        response.sendRedirect(
                request.getContextPath() + "/permission-register"
        );
    }
}