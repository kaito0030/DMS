package filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    @Override
    protected void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String uri = request.getRequestURI();

        // 認証不要のパス
        if (uri.endsWith("/login")
                || uri.endsWith("/index.html")
                || uri.contains("/css/")
                || uri.contains("/images/")
                || uri.contains("/js/")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("loginUser") == null) {

            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp")
                   .forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}