package servlet.api;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dto.PageResultDTO;
import dto.UserDTO;

@WebServlet("/api/users")
public class UserSearchApiServlet extends HttpServlet {

    private static final int LIMIT = 30;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String searchColumn = request.getParameter("searchColumn");
        String keyword = request.getParameter("keyword");

        int page = 1;

        String pageParam = request.getParameter("page");

        if (pageParam != null && !pageParam.isBlank()) {
            page = Integer.parseInt(pageParam);
        }

        int offset = (page - 1) * LIMIT;

        UserDAO userDAO = new UserDAO();

        PageResultDTO<UserDTO> result =
                userDAO.searchSortPaging(
                        searchColumn,
                        keyword,
                        "userName",
                        "asc",
                        LIMIT,
                        offset
                );

        int totalPages =
                (int) Math.ceil((double) result.getTotalCount() / LIMIT);

        if (totalPages == 0) {
            totalPages = 1;
        }

        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"currentPage\":").append(page).append(",");
        json.append("\"totalPages\":").append(totalPages).append(",");
        json.append("\"users\":[");

        List<UserDTO> userList = result.getList();

        for (int i = 0; i < userList.size(); i++) {

            UserDTO user = userList.get(i);

            json.append("{");
            json.append("\"userName\":\"").append(escapeJson(user.getUserName())).append("\",");
            json.append("\"realName\":\"").append(escapeJson(user.getRealName())).append("\"");
            json.append("}");

            if (i < userList.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        json.append("}");

        response.getWriter().write(json.toString());
    }

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n");
    }
}