package servlet.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DocumentDAO;
import dao.HistoryDAO;
import dto.DocumentDTO;
import dto.UserDTO;

@WebServlet("/document-pdf")
public class DocumentPdfServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String documentId = request.getParameter("documentId");

        DocumentDAO documentDAO = new DocumentDAO();
        DocumentDTO document = documentDAO.findByDocumentId(documentId);

        if (document == null
                || document.getPdfPath() == null
                || document.getPdfPath().isEmpty()) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 閲覧履歴登録
        HttpSession session = request.getSession(false);

        if (session != null) {
            UserDTO loginUser =
                    (UserDTO) session.getAttribute("loginUser");

            if (loginUser != null) {
                HistoryDAO historyDAO = new HistoryDAO();

                historyDAO.insert(
                        loginUser.getUserName(),
                        documentId
                );
            }
        }

        String realPath =
                getServletContext().getRealPath("/" + document.getPdfPath());

        File pdfFile = new File(realPath);

        if (!pdfFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "inline; filename=\"" + document.getDocumentId() + ".pdf\""
        );

        response.setContentLengthLong(pdfFile.length());

        try (
                FileInputStream in = new FileInputStream(pdfFile);
                OutputStream out = response.getOutputStream()
        ) {
            byte[] buffer = new byte[8192];

            int len;

            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }
}