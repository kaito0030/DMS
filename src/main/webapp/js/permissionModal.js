

let userCurrentPage = 1;
let userTotalPages = 1;

let documentCurrentPage = 1;
let documentTotalPages = 1;
document.addEventListener("DOMContentLoaded", function () {

    addClickEvent("openUserModalButton", function () {
        openModal("userModal");
        loadUsers(1);
    });

    addClickEvent("openDocumentModalButton", function () {
        openModal("documentModal");
        loadDocuments(1);
    });
	
	addClickEvent("searchUserButton", function () {
	    loadUsers(1);
	});
	
	addClickEvent("searchDocumentButton", function () {
	    loadDocuments(1);
	});
	
	addClickEvent("backUserPageButton", function () {
	    loadUsers(userCurrentPage-1);
	});

	addClickEvent("backDocumentPageButton", function () {
		    loadUsers(documentCurrentPage-1);
		});
	
	addClickEvent("nextUserPageButton", function () {
	    loadUsers(userCurrentPage+1);
	});
	
	addClickEvent("nextDocumentPageButton", function () {
		    loadUsers(documentCurrentPage+1);
		});

    addClickEvent("closeUserModalButton", function () {
        closeModal("userModal");
    });

    addClickEvent("closeDocumentModalButton", function () {
        closeModal("documentModal");
    });

    addClickEvent("openConfirmModalButton", function () {
        setPermissionConfirmText();
        openModal("confirmModal");
    });

    addClickEvent("confirmCancelButton", function () {
        closeModal("confirmModal");
    });

    addClickEvent("confirmSubmitButton", function () {
        document.getElementById("permissionRegisterForm").submit();
    });
});

function addClickEvent(id, callback) {

    const element = document.getElementById(id);

    if (element != null) {
        element.addEventListener("click", callback);
    }
}

function openModal(id) {

    document
        .getElementById(id)
        .classList
        .add("active");
}

function closeModal(id) {

    document
        .getElementById(id)
        .classList
        .remove("active");
}

function setPermissionConfirmText() {

    document.getElementById("confirmPermissionUser").textContent =
        document.getElementById("userNameInput").value;

    document.getElementById("confirmPermissionDocument").textContent =
        document.getElementById("documentIdInput").value;
}



function selectUser(userName) {
    document.getElementById("userNameInput").value = userName;
    closeModal("userModal");
}

function selectDocument(documentId) {
    document.getElementById("documentIdInput").value = documentId;
    closeModal("documentModal");
}

function loadUsers(page) {

    if (page < 1 || page > userTotalPages) {
        return;
    }

    const searchColumn =
        document.getElementById("userApiSearchColumn").value;

    const keyword =
        document.getElementById("userApiKeyword").value;

    fetch(contextPath
        + "/api/users?searchColumn="
        + encodeURIComponent(searchColumn)
        + "&keyword="
        + encodeURIComponent(keyword)
        + "&page="
        + page)
        .then(response => response.json())
        .then(data => {

            userCurrentPage = data.currentPage;
            userTotalPages = data.totalPages;

            const tbody =
                document.getElementById("userModalBody");

            tbody.innerHTML = "";

            data.users.forEach(function(user) {

                const tr = document.createElement("tr");
                tr.classList.add("selectable-row");

                tr.onclick = function() {
                    selectUser(user.userName);
                };

                tr.innerHTML =
                    "<td>" + escapeHtml(user.realName) + "</td>"
                    + "<td>" + escapeHtml(user.userName) + "</td>";

                tbody.appendChild(tr);
            });

            document.getElementById("userPageInfo").textContent =
                userCurrentPage + " / " + userTotalPages;
        });
}

function loadDocuments(page) {

    if (page < 1 || page > documentTotalPages) {
        return;
    }

    const searchColumn =
        document.getElementById("documentApiSearchColumn").value;

    const keyword =
        document.getElementById("documentApiKeyword").value;

    fetch(contextPath
        + "/api/documents?searchColumn="
        + encodeURIComponent(searchColumn)
        + "&keyword="
        + encodeURIComponent(keyword)
        + "&page="
        + page)
        .then(response => response.json())
        .then(data => {

            documentCurrentPage = data.currentPage;
            documentTotalPages = data.totalPages;

            const tbody =
                document.getElementById("documentModalBody");

            tbody.innerHTML = "";

            data.documents.forEach(function(documentData) {

                const tr = document.createElement("tr");
                tr.classList.add("selectable-row");

                tr.onclick = function() {
                    selectDocument(documentData.documentId);
                };

                tr.innerHTML =
                    "<td>" + escapeHtml(documentData.title) + "</td>"
                    + "<td>" + escapeHtml(documentData.author) + "</td>"
                    + "<td>" + escapeHtml(documentData.documentId) + "</td>";

                tbody.appendChild(tr);
            });

            document.getElementById("documentPageInfo").textContent =
                documentCurrentPage + " / " + documentTotalPages;
        });
}

function escapeHtml(value) {

    if (value == null) {
        return "";
    }

    return value
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll("\"", "&quot;")
        .replaceAll("'", "&#039;");
}

