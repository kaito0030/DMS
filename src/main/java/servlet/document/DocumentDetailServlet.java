package servlet.document;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DocumentDAO;
import dto.DocumentDTO;

@WebServlet("/document-detail")
public class DocumentDetailServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String documentId = request.getParameter("documentId");

        DocumentDAO documentDAO = new DocumentDAO();
        DocumentDTO document = documentDAO.findByDocumentId(documentId);

        request.setAttribute("document", document);
        request.setAttribute(
                "contentPage",
                "../document/documentDetail.jsp"
        );

        request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
               .forward(request, response);
    }
}