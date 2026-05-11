package servlet.user;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.HistoryDAO;
import dto.HistoryDTO;
import dto.UserDTO;

@WebServlet("/my-history")
public class MyHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO loginUser =
                (UserDTO) session.getAttribute("loginUser");

        HistoryDAO historyDAO = new HistoryDAO();

        List<HistoryDTO> historyList =
                historyDAO.findByUserName(
                        loginUser.getUserName()
                );

        request.setAttribute("historyList", historyList);
        request.setAttribute(
                "contentPage",
                "../user/myHistory.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }
}