package servlet.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.PermissionDAO;
import dao.UserDAO;
import dto.UserDTO;

@WebServlet("/user-register")
public class UserRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "contentPage",
                "../user/userRegister.jsp"
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

        String realName = request.getParameter("realName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String passwordConfirm =
                request.getParameter("passwordConfirm");
        String permissionType =
                request.getParameter("permissionType");

        boolean isAdmin = "allow".equals(permissionType);

        UserDAO userDAO = new UserDAO();

        if (userDAO.findByUserName(userName) != null) {

            request.setAttribute(
                    "userNameError",
                    "このユーザー名は使用されています"
            );

            request.setAttribute("realName", realName);
            request.setAttribute("userName", userName);
            request.setAttribute(
                    "contentPage",
                    "../user/userRegister.jsp"
            );

            request.getRequestDispatcher("/jsp/common/layout.jsp")
                   .forward(request, response);
            return;
        }

        if (!password.equals(passwordConfirm)) {

            request.setAttribute(
                    "passwordError",
                    "入力されたパスワードが異なります"
            );

            request.setAttribute("realName", realName);
            request.setAttribute("userName", userName);
            request.setAttribute(
                    "contentPage",
                    "../user/userRegister.jsp"
            );

            request.getRequestDispatcher("/jsp/common/layout.jsp")
                   .forward(request, response);
            return;
        }

        UserDTO user = new UserDTO(
                userName,
                realName,
                password,
                isAdmin
        );

        userDAO.insert(user);
        /*管理者権限を持つ人はすべての文書の閲覧権限を持つ*/
        if(isAdmin) {

            PermissionDAO permissionDAO = new PermissionDAO();

            permissionDAO.insertAllDocumentsForUser(userName);
        }
        response.sendRedirect(
                request.getContextPath() + "/user-list"
        );
    }
}