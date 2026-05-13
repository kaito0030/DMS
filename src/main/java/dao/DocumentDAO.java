package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DocumentDTO;
import dto.PageResultDTO;
import util.DBUtil;

public class DocumentDAO {
	/*閲覧可能コンテンツ*/
public PageResultDTO<DocumentDTO> findAvailableByUserNamePaging(
        String userName,
        int limit,
        int offset) {

    List<DocumentDTO> documentList = new ArrayList<>();
    int totalCount = 0;

    String sql = """
            SELECT d.document_id, d.title, d.author, d.publish_year, d.abstract_text, d.pdf_path,
                   COUNT(*) OVER() AS total_count
            FROM documents d
            JOIN document_permissions p
              ON d.document_id = p.document_id
            WHERE p.user_name = ?
            ORDER BY d.document_id
            LIMIT ? OFFSET ?
            """;

    try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {
        ps.setString(1, userName);
        ps.setInt(2, limit);
        ps.setInt(3, offset);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                documentList.add(new DocumentDTO(
						rs.getString("document_id"),
						rs.getString("title"),
						rs.getString("author"),
						rs.getInt("publish_year"),
						rs.getString("abstract_text"),
						rs.getString("pdf_path")));
                totalCount = rs.getInt("total_count");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return new PageResultDTO<>(documentList, totalCount);
}
	public List<DocumentDTO> findAll() {

		List<DocumentDTO> documentList = new ArrayList<>();

		String sql = """
				SELECT document_id, title, author, publish_year, abstract_text, pdf_path
				FROM documents
				ORDER BY document_id
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				documentList.add(new DocumentDTO(
						rs.getString("document_id"),
						rs.getString("title"),
						rs.getString("author"),
						rs.getInt("publish_year"),
						rs.getString("abstract_text"),
						rs.getString("pdf_path")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return documentList;
	}
    /*文書IDから文書リストを抽出*/
	public DocumentDTO findByDocumentId(String documentId) {

		String sql = """
				SELECT document_id, title, author, publish_year, abstract_text, pdf_path
				FROM documents
				WHERE document_id = ?
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, documentId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new DocumentDTO(
							rs.getString("document_id"),
							rs.getString("title"),
							rs.getString("author"),
							rs.getInt("publish_year"),
							rs.getString("abstract_text"),
							rs.getString("pdf_path"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public PageResultDTO<DocumentDTO> searchSortPaging(
			String searchColumn,
			String keyword,
			String sortColumn,
			String sortOrder,
			int limit,
			int offset) {

		List<DocumentDTO> documentList = new ArrayList<>();
		int totalCount = 0;

		StringBuilder sql = new StringBuilder("""
				SELECT document_id, title, author, publish_year, abstract_text, pdf_path,
				       COUNT(*) OVER() AS total_count
				FROM documents
				WHERE 1 = 1
				""");

		List<Object> params = new ArrayList<>();

		if (searchColumn != null && keyword != null && !keyword.isBlank()) {

			switch (searchColumn) {

			case "documentId":
				sql.append(" AND document_id LIKE ? ");
				params.add("%" + keyword + "%");
				break;

			case "title":
				sql.append(" AND title LIKE ? ");
				params.add("%" + keyword + "%");
				break;

			case "author":
				sql.append(" AND author LIKE ? ");
				params.add("%" + keyword + "%");
				break;
			}
		}

		String orderColumn = "document_id";

		if ("title".equals(sortColumn)) {
			orderColumn = "title";
		} else if ("author".equals(sortColumn)) {
			orderColumn = "author";
		} else if ("publishYear".equals(sortColumn)) {
			orderColumn = "publish_year";
		}

		String order = "ASC";

		if ("desc".equalsIgnoreCase(sortOrder)) {
			order = "DESC";
		}

		sql.append(" ORDER BY ")
				.append(orderColumn)
				.append(" ")
				.append(order)
				.append(" LIMIT ? OFFSET ? ");

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString())) {

			int index = 1;

			for (Object param : params) {
				ps.setString(index++, param.toString());
			}

			ps.setInt(index++, limit);
			ps.setInt(index, offset);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					documentList.add(new DocumentDTO(
							rs.getString("document_id"),
							rs.getString("title"),
							rs.getString("author"),
							rs.getInt("publish_year"),
							rs.getString("abstract_text"),
							rs.getString("pdf_path")));
					totalCount = rs.getInt("total_count");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new PageResultDTO<>(documentList, totalCount);
	}
	

	
	public PageResultDTO<DocumentDTO> searchPaging(
	        String searchColumn,
	        String keyword,
	        int limit,
	        int offset) {

	    List<DocumentDTO> documentList = new ArrayList<>();
	    int totalCount = 0;

	    StringBuilder sql = new StringBuilder("""
	            SELECT document_id, title, author, publish_year, abstract_text, pdf_path,
	                   COUNT(*) OVER() AS total_count
	            FROM documents
	            WHERE 1 = 1
	            """);

	    List<Object> params = new ArrayList<>();

	    if (searchColumn != null && keyword != null && !keyword.isBlank()) {

	        switch (searchColumn) {

	            case "documentId":
	                sql.append(" AND document_id LIKE ? ");
	                params.add("%" + keyword + "%");
	                break;

	            case "author":
	                sql.append(" AND author LIKE ? ");
	                params.add("%" + keyword + "%");
	                break;

	            case "title":
	                sql.append(" AND title LIKE ? ");
	                params.add("%" + keyword + "%");
	                break;
	        }
	    }

	    sql.append("""
	            ORDER BY document_id
	            LIMIT ? OFFSET ?
	            """);

	    try (
	            Connection con = DBUtil.getConnection();
	            PreparedStatement ps = con.prepareStatement(sql.toString())
	    ) {
	        int index = 1;

	        for (Object param : params) {
	            ps.setString(index++, param.toString());
	        }

	        ps.setInt(index++, limit);
	        ps.setInt(index, offset);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                documentList.add(new DocumentDTO(
							rs.getString("document_id"),
							rs.getString("title"),
							rs.getString("author"),
							rs.getInt("publish_year"),
							rs.getString("abstract_text"),
							rs.getString("pdf_path")));
	                totalCount = rs.getInt("total_count");
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return new PageResultDTO<>(documentList, totalCount);
	}

	public boolean insert(DocumentDTO document) {

		String sql = """
				INSERT INTO documents
				(document_id, title, author, publish_year, abstract_text, pdf_path)
				VALUES (?, ?, ?, ?, ?, ?)
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, document.getDocumentId());
			ps.setString(2, document.getTitle());
			ps.setString(3, document.getAuthor());
			ps.setInt(4, document.getPublishYear());
			ps.setString(5, document.getAbstractText());
			ps.setString(6, document.getPdfPath());

			return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean update(DocumentDTO document) {

		String sql = """
				UPDATE documents
				SET title = ?,
				    author = ?,
				    publish_year = ?,
				    abstract_text = ?,
				    pdf_path = ?
				WHERE document_id = ?
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, document.getTitle());
			ps.setString(2, document.getAuthor());
			ps.setInt(3, document.getPublishYear());
			ps.setString(4, document.getAbstractText());
			ps.setString(5, document.getPdfPath());
			ps.setString(6, document.getDocumentId());

			return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean delete(String documentId) {

		String sql = """
				DELETE FROM documents
				WHERE document_id = ?
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, documentId);

			return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}




}