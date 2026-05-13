package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PageResultDTO;
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
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				UserDTO user = new UserDTO(
						rs.getString("user_name"),
						rs.getString("real_name"),
						rs.getString("password"),
						rs.getBoolean("is_admin"));

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
				WHERE is_admin = TRUE
				ORDER BY user_name
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				userList.add(new UserDTO(
						rs.getString("user_name"),
						rs.getString("real_name"),
						rs.getString("password"),
						rs.getBoolean("admin")));
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
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userName);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return new UserDTO(
							rs.getString("user_name"),
							rs.getString("real_name"),
							rs.getString("password"),
							rs.getBoolean("is_admin"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public PageResultDTO<UserDTO> searchSortPaging(
	        String searchColumn,
	        String keyword,
	        String sortColumn,
	        String sortOrder,
	        int limit,
	        int offset) {

	    List<UserDTO> userList = new ArrayList<>();
	    int totalCount = 0;

	    StringBuilder sql = new StringBuilder("""
	            SELECT user_name, real_name, password, is_admin,
	                   COUNT(*) OVER() AS total_count
	            FROM users
	            WHERE 1 = 1
	            """);
	    
	    List<Object> params = new ArrayList<>();

	    if (searchColumn != null && keyword != null && !keyword.isBlank()) {

	        switch (searchColumn) {

	            case "userName":
	                sql.append(" AND user_name LIKE ? ");
	                params.add("%" + keyword + "%");
	                break;

	            case "realName":
	                sql.append(" AND real_name LIKE ? ");
	                params.add("%" + keyword + "%");
	                break;

	            case "userType":
	                sql.append(" AND is_admin = ? ");
	                params.add("admin".equals(keyword));
	                break;
	        }
	    }
	    
	    String orderColumn = "user_name";

	    if ("realName".equals(sortColumn)) {
	        orderColumn = "real_name";
	    } else if ("userType".equals(sortColumn)) {
	        orderColumn = "is_admin";
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
	            PreparedStatement ps = con.prepareStatement(sql.toString())
	    ) {
	        int index = 1;

	        for (Object param : params) {

	            if (param instanceof Boolean) {
	                ps.setBoolean(index++, (Boolean) param);
	            } else {
	                ps.setString(index++, param.toString());
	            }
	        }

	        ps.setInt(index++, limit);
	        ps.setInt(index, offset);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                userList.add(new UserDTO(
	                        rs.getString("user_name"),
	                        rs.getString("real_name"),
	                        rs.getString("password"),
	                        rs.getBoolean("is_admin")
	                ));

	                totalCount = rs.getInt("total_count");
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return new PageResultDTO<>(userList, totalCount);
	}
	
	public boolean insert(UserDTO user) {

		String sql = """
				INSERT INTO users
				(user_name, real_name, password, is_admin)
				VALUES (?, ?, ?, ?)
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

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
				    password = ?,
				    is_admin = ?
				WHERE user_name = ?
				""";

		try (
				Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

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
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userName);

			return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}