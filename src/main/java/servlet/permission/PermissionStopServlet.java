package servlet.permission;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.PermissionDAO;

@WebServlet("/permission-stop")
public class PermissionStopServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String userName =
                request.getParameter("userName");

        String documentId =
                request.getParameter("documentId");

        PermissionDAO permissionDAO =
                new PermissionDAO();

        permissionDAO.deletePermission(
                userName,
                documentId
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/user-permission-list"
                        + "?userName=" + userName
        );
    }
}