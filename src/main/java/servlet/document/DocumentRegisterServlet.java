package servlet.document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dao.DocumentDAO;
import dao.PermissionDAO;
import dao.UserDAO;
import dto.DocumentDTO;
import dto.UserDTO;
import util.FileUtil;

@WebServlet("/document-register")
@MultipartConfig
public class DocumentRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "contentPage",
                "../document/documentRegister.jsp"
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

        String documentId = request.getParameter("documentId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publishYearStr = request.getParameter("publishYear");
        String abstractText = request.getParameter("abstractText");

        DocumentDAO documentDAO = new DocumentDAO();

        if (documentDAO.findByDocumentId(documentId) != null) {

            request.setAttribute("documentIdError", "この文書IDは使用されています");

            request.setAttribute("documentId", documentId);
            request.setAttribute("title", title);
            request.setAttribute("author", author);
            request.setAttribute("publishYear", publishYearStr);
            request.setAttribute("abstractText", abstractText);

            request.setAttribute(
                    "contentPage",
                    "../document/documentRegister.jsp"
            );

            request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
                   .forward(request, response);
            return;
        }

        Part pdfPart = request.getPart("pdfFile");

        if (pdfPart == null || pdfPart.getSize() == 0) {

            request.setAttribute("fileError", "PDFファイルを選択してください");

            request.setAttribute("documentId", documentId);
            request.setAttribute("title", title);
            request.setAttribute("author", author);
            request.setAttribute("publishYear", publishYearStr);
            request.setAttribute("abstractText", abstractText);

            request.setAttribute(
                    "contentPage",
                    "../document/documentRegister.jsp"
            );

            request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
                   .forward(request, response);
            return;
        }

        String originalFileName =
                Paths.get(pdfPart.getSubmittedFileName())
                     .getFileName()
                     .toString();

        String contentType = pdfPart.getContentType();

        if (!originalFileName.toLowerCase().endsWith(".pdf")
                || !"application/pdf".equals(contentType)) {

            request.setAttribute("fileError", "PDFファイルのみアップロード可能です");

            request.setAttribute("documentId", documentId);
            request.setAttribute("title", title);
            request.setAttribute("author", author);
            request.setAttribute("publishYear", publishYearStr);
            request.setAttribute("abstractText", abstractText);

            request.setAttribute(
                    "contentPage",
                    "../document/documentRegister.jsp"
            );

            request.getRequestDispatcher("/WEB-INF/jsp/common/layout.jsp")
                   .forward(request, response);
            return;
        }

        int publishYear = Integer.parseInt(publishYearStr);

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

        DocumentDTO document = new DocumentDTO(
                documentId,
                title,
                author,
                publishYear,
                abstractText,
                savePath
        );

        documentDAO.insert(document);

        UserDAO userDAO = new UserDAO();
        PermissionDAO permissionDAO = new PermissionDAO();

        List<UserDTO> adminList = userDAO.findAllAdmins();

        for (UserDTO adminUser : adminList) {
            permissionDAO.insertPermission(
                    adminUser.getUserName(),
                    documentId
            );
        }

        response.sendRedirect(
                request.getContextPath() + "/document-list"
        );
    }
}