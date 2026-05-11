package servlet.user;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.HistoryDAO;
import dto.HistoryDTO;

@WebServlet("/user-history")
public class UserHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");

        HistoryDAO historyDAO = new HistoryDAO();

        List<HistoryDTO> historyList =
                historyDAO.findByUserName(userName);

        request.setAttribute("targetUserName", userName);
        request.setAttribute("historyList", historyList);
        request.setAttribute(
                "contentPage",
                "../user/userHistory.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }
}