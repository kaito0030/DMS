package servlet.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DocumentDAO;
import dto.DocumentDTO;
import dto.PageResultDTO;
import dto.UserDTO;

@WebServlet("/available-content")
public class AvailableContentServlet extends HttpServlet {
	private static final int LIMIT = 30;

	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		int page = 1;

		String pageParam = request.getParameter("page");

		if (pageParam != null && !pageParam.isBlank()) {
			page = Integer.parseInt(pageParam);
		}

		int offset = (page - 1) * LIMIT;

		HttpSession session = request.getSession(false);
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

		DocumentDAO documentDAO = new DocumentDAO();

		PageResultDTO<DocumentDTO> result = documentDAO.findAvailableByUserNamePaging(
				loginUser.getUserName(),
				LIMIT,
				offset);

		int totalPages = (int) Math.ceil((double) result.getTotalCount() / LIMIT);

		if (totalPages == 0) {
			totalPages = 1;
		}

		request.setAttribute("documentList", result.getList());
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("contentPage", "../user/availableContent.jsp");

		request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
				.forward(request, response);
	}
}