package vnua.fita.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import vnua.fita.bean.Clothes;
import vnua.fita.service.ClothesService;


public class ClothesServiceImpl implements ClothesService {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection conn;
	
	// Constructor với các tham số để kết nối 
	public ClothesServiceImpl(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	public List<Clothes> search(String keyword){
		List<Clothes> result = new LinkedList<Clothes>();
		PreparedStatement pst = null;
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			String sqlCommand ="Select * from tbl_clothes ";
			
			// Mệnh đề Where chỉ xuất hiện trong câu sql khi keyword khác null, khác rỗng
			if ((keyword!=null && !"".equals(keyword))){
				sqlCommand += "WHERE "
								+ "name LIKE ? "
								+ " OR "
								+ "brand LIKE ? "
								+ " OR "
								+ "origin LIKE ? ";
			}
			
			pst = conn.prepareStatement(sqlCommand);
			if ((keyword!=null && !"".equals(keyword))){
				pst.setString(1, "%" + keyword.toLowerCase() + "%");
				pst.setString(2, "%" + keyword.toLowerCase() + "%");
				pst.setString(3, "%" + keyword.toLowerCase() + "%");
			}
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String brand = rs.getString("brand");
				Integer quantity = rs.getInt("quantity");
				String origin = rs.getString("origin");
				String size = rs.getString("size");
				String preview = rs.getString("preview");
				Clothes clothes = new Clothes(id, name, price, brand, quantity, origin, size, preview);
				result.add(clothes);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.closeConnect(conn);
			DBConnection.closeStatement(pst);
		}
		return result;
	}
	
	public boolean insert(Clothes clothes) {
		PreparedStatement pst = null;
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			String sqlCommand ="INSERT INTO tbl_clothes(name, price, brand, quantity, origin, size, preview) VALUES (?, ?, ?, ?, ?, ?, ?) ";
			
			pst = conn.prepareStatement(sqlCommand, PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, clothes.getName());
			pst.setInt(2, clothes.getPrice());
			pst.setString(3, clothes.getBrand());
			pst.setInt(4, clothes.getQuantity());
			pst.setString(5, clothes.getOrigin());
			pst.setString(6, clothes.getSize());
			pst.setString(7, clothes.getPreview());
			
			return pst.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.closeConnect(conn);
			DBConnection.closeStatement(pst);
		}
		return false;
	}
	
	public boolean update(Clothes clothes) {
		PreparedStatement pst = null;
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			String sqlCommand ="UPDATE tbl_clothes SET name = ?, price = ?, brand = ?, quantity = ?, "
					+ "origin = ?, size = ?, preview = ? WHERE id = ?";
			
			pst = conn.prepareStatement(sqlCommand);
			
			int index = 1;
			pst.setString(index++, clothes.getName());
			pst.setInt(index++, clothes.getPrice());
			pst.setString(index++, clothes.getBrand());
			pst.setInt(index++, clothes.getQuantity());
			pst.setString(index++, clothes.getOrigin());
			pst.setString(index++, clothes.getSize());
			pst.setString(index++, clothes.getPreview());
			pst.setInt(index++, clothes.getId());
			
			return pst.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.closeConnect(conn);
			DBConnection.closeStatement(pst);
		}
		return false;
	}
	
	// Việc cài đặt dành cho sinh viên và bạn đọc
	public boolean delete(int clothesId) {
		PreparedStatement pst = null;
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			String sqlCommand ="DELETE FROM tbl_clothes WHERE id = ?";
			
			pst = conn.prepareStatement(sqlCommand);
			
			int index = 1;
			pst.setInt(index++, clothesId);
			
			return pst.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.closeConnect(conn);
			DBConnection.closeStatement(pst);
		}
		return false;
	}
	
	// Việc cài đặt dành cho sinh viên và bạn đọc
	public Clothes select(int clothesId) {
		Clothes result = null;
		PreparedStatement pst = null;
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			String sqlCommand ="Select * from tbl_clothes where id = ?";
			
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setInt(1, clothesId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String brand = rs.getString("brand");
				Integer quantity = rs.getInt("quantity");
				String origin = rs.getString("origin");
				String size = rs.getString("size");
				String preview = rs.getString("preview");
				result = new Clothes(id, name, price, brand, quantity, origin, size, preview);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.closeConnect(conn);
			DBConnection.closeStatement(pst);
		}
		return result;
	}
	
}
