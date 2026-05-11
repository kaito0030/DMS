package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DocumentDTO;
import util.DBUtil;

public class DocumentDAO {
	/*閲覧可能コンテンツ*/
	public List<DocumentDTO> findAvailableByUserName(String userName) {

		List<DocumentDTO> documentList = new ArrayList<>();
        /*userNameと一致し、閲覧権限がtrueの文書を取り出す*/
		String sql = """
				SELECT
				    d.document_id,
				    d.title,
				    d.author,
				    d.publish_year,
				    d.abstract_text,
				    d.pdf_path
				FROM documents d
				JOIN document_permissions p
				  ON d.document_id = p.document_id
				WHERE p.user_name = ?
				ORDER BY d.document_id
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userName);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					documentList.add(mapToDTO(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return documentList;
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
				documentList.add(mapToDTO(rs));
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
					return mapToDTO(rs);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	/*タイトルor著者検索*/
	public List<DocumentDTO> search(String keyword) {

		List<DocumentDTO> documentList = new ArrayList<>();

		String sql = """
				SELECT document_id, title, author, publish_year, abstract_text, pdf_path
				FROM documents
				WHERE title LIKE ?
				   OR author LIKE ?
				ORDER BY document_id
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			String key = "%" + keyword + "%";

			ps.setString(1, key);
			ps.setString(2, key);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					documentList.add(mapToDTO(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return documentList;
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

	private DocumentDTO mapToDTO(ResultSet rs) throws SQLException {

		return new DocumentDTO(
				rs.getString("document_id"),
				rs.getString("title"),
				rs.getString("author"),
				rs.getInt("publish_year"),
				rs.getString("abstract_text"),
				rs.getString("pdf_path"));
	}
}