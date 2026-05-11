package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.HistoryDTO;
import util.DBUtil;

public class HistoryDAO {

    public List<HistoryDTO> findByUserName(String userName) {

        List<HistoryDTO> historyList = new ArrayList<>();

        String sql = """
                SELECT
                    u.user_name,
                    u.real_name,
                    d.document_id,
                    d.title,
                    d.author,
                    TO_CHAR(h.viewed_at, 'YYYY/MM/DD HH24:MI') AS viewed_at
                FROM view_histories h
                JOIN users u
                  ON h.user_name = u.user_name
                JOIN documents d
                  ON h.document_id = d.document_id
                WHERE h.user_name = ?
                ORDER BY h.viewed_at DESC
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    historyList.add(new HistoryDTO(
                            rs.getString("user_name"),
                            rs.getString("real_name"),
                            rs.getString("document_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("viewed_at")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    public boolean insert(String userName, String documentId) {

        String sql = """
                INSERT INTO view_histories
                (user_name, document_id)
                VALUES (?, ?)
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);
            ps.setString(2, documentId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}