package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PermissionDTO;
import util.DBUtil;

public class PermissionDAO {

    public List<PermissionDTO> findAll() {

        List<PermissionDTO> permissionList = new ArrayList<>();

        String sql = """
                SELECT user_name, document_id
                FROM document_permissions
                ORDER BY user_name, document_id
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                permissionList.add(new PermissionDTO(
                        rs.getString("user_name"),
                        rs.getString("document_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permissionList;
    }

    public boolean existsPermission(String userName, String documentId) {

        String sql = """
                SELECT 1
                FROM document_permissions
                WHERE user_name = ?
                  AND document_id = ?
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);
            ps.setString(2, documentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertPermission(String userName, String documentId) {

        String sql = """
                INSERT INTO document_permissions
                (user_name, document_id)
                VALUES (?, ?)
                ON CONFLICT (user_name, document_id)
                DO NOTHING
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);
            ps.setString(2, documentId);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deletePermission(String userName, String documentId) {

        String sql = """
                DELETE FROM document_permissions
                WHERE user_name = ?
                  AND document_id = ?
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);
            ps.setString(2, documentId);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteAllByUserName(String userName) {

        String sql = """
                DELETE FROM document_permissions
                WHERE user_name = ?
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertAllDocumentsForUser(String userName) {

        String sql = """
                INSERT INTO document_permissions
                (user_name, document_id)
                SELECT ?, document_id
                FROM documents
                ON CONFLICT (user_name, document_id)
                DO NOTHING
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}