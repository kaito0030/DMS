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

@WebServlet("/document-search-result")
public class DocumentSearchResultServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("keyword");

        DocumentDAO documentDAO = new DocumentDAO();

        List<DocumentDTO> documentList;

        if (keyword == null || keyword.isBlank()) {
            documentList = documentDAO.findAll();
        } else {
            documentList = documentDAO.search(keyword);
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("documentList", documentList);
        request.setAttribute(
                "contentPage",
                "../document/documentSearchResult.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }
}