package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UserDTO;
import util.DBUtil;

public class UserDAO {

    public List<UserDTO> findAll() {

        List<UserDTO> userList = new ArrayList<>();

        String sql = """
                SELECT user_name, real_name, password, is_admin
                FROM users
                ORDER BY user_name
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getString("user_name"),
                        rs.getString("real_name"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );

                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
    
    public List<UserDTO> findAllAdmins() {

        List<UserDTO> userList = new ArrayList<>();

        String sql = """
                SELECT user_name, real_name, password, , is_admin
                FROM users
                WHERE admin = TRUE
                ORDER BY user_name
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                userList.add(new UserDTO(
                        rs.getString("user_name"),
                        rs.getString("real_name"),
                        rs.getString("password"),
                        rs.getBoolean("admin")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public UserDTO findByUserName(String userName) {

        String sql = """
                SELECT user_name, real_name, password, is_admin
                FROM users
                WHERE user_name = ?
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new UserDTO(
                            rs.getString("user_name"),
                            rs.getString("real_name"),
                            rs.getString("password"),
                            rs.getBoolean("is_admin")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insert(UserDTO user) {

        String sql = """
                INSERT INTO users
                (user_name, real_name, password, is_admin)
                VALUES (?, ?, ?,?)
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getRealName());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.getAdmin());
            

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(String oldUserName, UserDTO user) {

        String sql = """
                UPDATE users
                SET user_name = ?,
                    real_name = ?,
                    password = ?
                    is_admin = ?
                WHERE user_name = ?
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getRealName());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.getAdmin());
            ps.setString(5, oldUserName);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(String userName) {

        String sql = """
                DELETE FROM users
                WHERE user_name = ?
                """;

        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, userName);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}