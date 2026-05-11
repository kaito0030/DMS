package filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dto.UserDTO;

@WebFilter({
        "/user-list",
        "/user-edit",
        "/user-register",
        "/user-history",
        "/document-list",
        "/document-edit",
        "/document-register",
        "/permission-register"
})
public class AdminFilter extends HttpFilter {

    @Override
    protected void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("loginUser") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        UserDTO loginUser =
                (UserDTO) session.getAttribute("loginUser");

        if (!loginUser.getAdmin()) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "管理者権限がありません"
            );
            return;
        }

        chain.doFilter(request, response);
    }
}