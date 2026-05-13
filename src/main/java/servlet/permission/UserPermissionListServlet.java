package servlet.permission;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DocumentDAO;
import dto.DocumentDTO;
import dto.PageResultDTO;

@WebServlet("/user-permission-list")
public class UserPermissionListServlet extends HttpServlet {
	private static final int LIMIT = 30;

	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");

		int page = 1;

		String pageParam = request.getParameter("page");

		if (pageParam != null && !pageParam.isBlank()) {
			page = Integer.parseInt(pageParam);
		}

		int offset = (page - 1) * LIMIT;

		DocumentDAO documentDAO = new DocumentDAO();

		PageResultDTO<DocumentDTO> result = documentDAO.findAvailableByUserNamePaging(
				userName,
				LIMIT,
				offset);

		int totalPages = (int) Math.ceil((double) result.getTotalCount() / LIMIT);

		if (totalPages == 0) {
			totalPages = 1;
		}

		request.setAttribute("targetUserName", userName);
		request.setAttribute("documentList", result.getList());
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("contentPage", "../permission/userPermissionList.jsp");

		request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
				.forward(request, response);
	}
}