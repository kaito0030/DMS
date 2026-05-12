package servlet.document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dao.DocumentDAO;
import dto.DocumentDTO;
import util.FileUtil;

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
        request.setAttribute(
                "contentPage",
                "../document/documentEdit.jsp"
        );

        request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
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

        DocumentDTO oldDocument =
                documentDAO.findByDocumentId(documentId);

        if ("delete".equals(action)) {

            deletePhysicalFile(oldDocument);

            documentDAO.delete(documentId);

            response.sendRedirect(
                    request.getContextPath() + "/document-list"
            );
            return;
        }

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

            String contentType = pdfPart.getContentType();

            if (!originalFileName.toLowerCase().endsWith(".pdf")
                    || !"application/pdf".equals(contentType)) {

                request.setAttribute("fileError", "PDFファイルのみアップロード可能です");
                request.setAttribute("document", oldDocument);

                request.setAttribute(
                        "contentPage",
                        "../document/documentEdit.jsp"
                );

                request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
                       .forward(request, response);
                return;
            }

            deletePhysicalFile(oldDocument);

            File uploadDir = new File(FileUtil.PDF_DIRECTORY);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String savedFileName = UUID.randomUUID().toString() + ".pdf";

            String savePath =
                    FileUtil.PDF_DIRECTORY
                            + File.separator
                            + savedFileName;

            pdfPart.write(savePath);

            pdfPath = savePath;
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

    private void deletePhysicalFile(DocumentDTO document) {

        if (document == null
                || document.getPdfPath() == null
                || document.getPdfPath().isEmpty()) {
            return;
        }

        File file = new File(document.getPdfPath());

        if (file.exists()) {
            file.delete();
        }
    }
}