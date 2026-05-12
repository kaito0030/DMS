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
import util.PasswordUtil;
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

        request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
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
        String admin =
                request.getParameter("admin");

        boolean isAdmin = "true".equals(admin);

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

            request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
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

            request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
                   .forward(request, response);
            return;
          
        }
        String passwordPattern =
                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        if (!password.matches(passwordPattern)) {

            request.setAttribute(
                    "passwordRuleError",
                    "パスワードは英字と数字を含む8文字以上で入力してください"
            );

            request.setAttribute("realName", realName);
            request.setAttribute("userName", userName);

            request.setAttribute(
                    "contentPage",
                    "../user/userRegister.jsp"
            );

            request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
                   .forward(request, response);

            return;
        }

        String hashedPassword =
                PasswordUtil.hashPassword(password);

        UserDTO user = new UserDTO(
                userName,
                realName,
                hashedPassword,
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