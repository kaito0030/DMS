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

@WebServlet("/document-list")
public class DocumentListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String searchColumn = request.getParameter("searchColumn");
        String keyword = request.getParameter("keyword");

        DocumentDAO documentDAO = new DocumentDAO();

        List<DocumentDTO> documentList;

        if (searchColumn == null || keyword == null || keyword.isBlank()) {
            documentList = documentDAO.findAll();
        } else {
            documentList = documentDAO.searchByColumn(searchColumn, keyword);
        }

        request.setAttribute("documentList", documentList);
        request.setAttribute("searchColumn", searchColumn);
        request.setAttribute("keyword", keyword);

        request.setAttribute(
                "contentPage",
                "../document/documentList.jsp"
        );

        request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
               .forward(request, response);
    }
}