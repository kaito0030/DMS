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

@WebServlet("/user-edit")
public class UserEditServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");

        UserDAO userDAO = new UserDAO();
        UserDTO user = userDAO.findByUserName(userName);

        request.setAttribute("user", user);
        request.setAttribute(
                "contentPage",
                "../user/userEdit.jsp"
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

        String action = request.getParameter("action");
        String oldUserName = request.getParameter("oldUserName");

        UserDAO userDAO = new UserDAO();

        if ("delete".equals(action)) {
            userDAO.delete(oldUserName);

            response.sendRedirect(
                    request.getContextPath() + "/user-list"
            );
            return;
        }

        String realName = request.getParameter("realName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String passwordConfirm =
                request.getParameter("passwordConfirm");
        String admin =
                request.getParameter("admin");

        boolean isAdmin = "true".equals(admin);

        UserDTO oldUser =
                userDAO.findByUserName(oldUserName);

        if (!oldUserName.equals(userName)
                && userDAO.findByUserName(userName) != null) {

            request.setAttribute(
                    "userNameError",
                    "このユーザー名は使用されています"
            );

            request.setAttribute("user", oldUser);
            request.setAttribute(
                    "contentPage",
                    "../user/userEdit.jsp"
            );

            request.getRequestDispatcher("/jsp/common/layout.jsp")
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

            request.setAttribute("user", oldUser);

            request.setAttribute(
                    "contentPage",
                    "../user/userEdit.jsp"
            );

            request.getRequestDispatcher("/jsp/common/layout.jsp")
                   .forward(request, response);

            return;
        }
        String newPassword = oldUser.getPassword();

        if (password != null && !password.isBlank()) {

            if (!password.equals(passwordConfirm)) {

                request.setAttribute(
                        "passwordError",
                        "入力されたパスワードが異なります"
                );

                request.setAttribute("user", oldUser);
                request.setAttribute(
                        "contentPage",
                        "../user/userEdit.jsp"
                );

                request.getRequestDispatcher("/jsp/common/layout.jsp")
                       .forward(request, response);
                return;
            }

            newPassword = password;
        }
        newPassword =
                PasswordUtil.hashPassword(password);
        UserDTO updateUser = new UserDTO(
                userName,
                realName,
                newPassword,
                isAdmin
                
        );

        userDAO.update(oldUserName, updateUser);
        
        if (oldUser.getAdmin()^isAdmin) {
            	PermissionDAO permissionDAO = new PermissionDAO();
            if(isAdmin) {
            	    permissionDAO.insertAllDocumentsForUser(userName);
            }else {
            	    permissionDAO.deleteAllByUserName(userName);
            }
        }

        response.sendRedirect(
                request.getContextPath() + "/user-list"
        );
    }
}