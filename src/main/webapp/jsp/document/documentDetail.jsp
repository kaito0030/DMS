<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<h1>文書詳細</h1>

<div class="detail-card">

    <div class="detail-row">
        <div class="detail-label">文書ID</div>
        <div class="detail-value">${document.documentId}</div>
    </div>

    <div class="detail-row">
        <div class="detail-label">文書タイトル</div>
        <div class="detail-value">${document.title}</div>
    </div>

    <div class="detail-row">
        <div class="detail-label">著者</div>
        <div class="detail-value">${document.author}</div>
    </div>

    <div class="detail-row">
        <div class="detail-label">発行年</div>
        <div class="detail-value">${document.publishYear}</div>
    </div>

    <div class="detail-row">
        <div class="detail-label">アブストラクト</div>
        <div class="detail-value">${document.abstractText}</div>
    </div>

    <div class="detail-row">
        <div class="detail-label">PDF</div>
        <div class="detail-value">
            <a href="${pageContext.request.contextPath}/${document.pdfPath}"
               target="_blank">
                PDFを開く
            </a>
        </div>
    </div>

</div>