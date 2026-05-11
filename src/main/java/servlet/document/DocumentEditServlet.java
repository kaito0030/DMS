package servlet.document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dao.DocumentDAO;
import dto.DocumentDTO;

@WebServlet("/document-edit")
@MultipartConfig
public class DocumentEditServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String documentId = request.getParameter("documentId");

        DocumentDAO documentDAO = new DocumentDAO();

        DocumentDTO document =
                documentDAO.findByDocumentId(documentId);

        request.setAttribute("document", document);
        request.setAttribute("contentPage", "../document/documentEdit.jsp");

        request.getRequestDispatcher("/jsp/common/layout.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String documentId = request.getParameter("documentId");

        DocumentDAO documentDAO = new DocumentDAO();

        if ("delete".equals(action)) {

            documentDAO.delete(documentId);

            response.sendRedirect(
                    request.getContextPath() + "/document-list"
            );
            return;
        }

        DocumentDTO oldDocument =
                documentDAO.findByDocumentId(documentId);

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int publishYear =
                Integer.parseInt(request.getParameter("publishYear"));
        String abstractText = request.getParameter("abstractText");

        String pdfPath = oldDocument.getPdfPath();

        Part pdfPart = request.getPart("pdfFile");

        if (pdfPart != null && pdfPart.getSize() > 0) {

            String originalFileName =
                    Paths.get(pdfPart.getSubmittedFileName())
                         .getFileName()
                         .toString();

            String saveFileName = documentId + "_" + originalFileName;

            String uploadDirPath =
                    getServletContext().getRealPath("/pdf");

            File uploadDir = new File(uploadDirPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String savePath = uploadDirPath + File.separator + saveFileName;

            pdfPart.write(savePath);

            pdfPath = "pdf/" + saveFileName;
        }

        DocumentDTO document = new DocumentDTO(
                documentId,
                title,
                author,
                publishYear,
                abstractText,
                pdfPath
        );

        documentDAO.update(document);

        response.sendRedirect(
                request.getContextPath() + "/document-list"
        );
    }
}