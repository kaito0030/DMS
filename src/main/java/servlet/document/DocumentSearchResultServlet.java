package servlet.document;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DocumentDAO;
import dto.DocumentDTO;
import dto.PageResultDTO;

@WebServlet("/document-search-result")
public class DocumentSearchResultServlet extends HttpServlet {
	private static final int LIMIT = 30;

	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String searchColumn = request.getParameter("searchColumn");
		String keyword = request.getParameter("keyword");

		int page = 1;

		String pageParam = request.getParameter("page");

		if (pageParam != null && !pageParam.isBlank()) {
			page = Integer.parseInt(pageParam);
		}

		int offset = (page - 1) * LIMIT;

		DocumentDAO documentDAO = new DocumentDAO();

		PageResultDTO<DocumentDTO> result = documentDAO.searchPaging(
				searchColumn,
				keyword,
				LIMIT,
				offset);

		int totalPages = (int) Math.ceil((double) result.getTotalCount() / LIMIT);

		if (totalPages == 0) {
			totalPages = 1;
		}

		request.setAttribute("documentList", result.getList());
		request.setAttribute("searchColumn", searchColumn);
		request.setAttribute("keyword", keyword);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("contentPage", "../document/documentSearchResult.jsp");

		request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
				.forward(request, response);
	}
}