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

        DocumentDAO documentDAO = new DocumentDAO();

        List<DocumentDTO> documentList = documentDAO.findAll();

        request.setAttribute("documentList", documentList);
        request.setAttribute(
                "contentPage",
                "../document/documentList.jsp"
        );

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }
}