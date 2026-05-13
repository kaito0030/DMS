package servlet.document;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DocumentDAO;
import dto.DocumentDTO;
import dto.PageResultDTO;

@WebServlet("/document-list")
public class DocumentListServlet extends HttpServlet {

    private static final int LIMIT = 30;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String searchColumn = request.getParameter("searchColumn");
        String keyword = request.getParameter("keyword");
        String sortColumn = request.getParameter("sortColumn");
        String sortOrder = request.getParameter("sortOrder");

        int page = 1;

        String pageParam = request.getParameter("page");

        if (pageParam != null && !pageParam.isBlank()) {
            page = Integer.parseInt(pageParam);
        }

        int offset = (page - 1) * LIMIT;

        DocumentDAO documentDAO = new DocumentDAO();

        PageResultDTO<DocumentDTO> result =
                documentDAO.searchSortPaging(
                        searchColumn,
                        keyword,
                        sortColumn,
                        sortOrder,
                        LIMIT,
                        offset
                );
        List<DocumentDTO> documentList=result.getList();
        int totalCount =result.getTotalCount();

        int totalPages =
                (int) Math.ceil((double) totalCount / LIMIT);
        if (totalPages == 0) {
            totalPages = 1;
        }

        request.setAttribute("documentList", documentList);
        request.setAttribute("searchColumn", searchColumn);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortColumn", sortColumn);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute(
                "contentPage",
                "../document/documentList.jsp"
        );

        request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
               .forward(request, response);
    }
}