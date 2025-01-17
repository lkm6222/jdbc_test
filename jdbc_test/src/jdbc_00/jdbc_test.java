package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class jdbc_test {
	
	public static void main(String[] args) {
	
		final String driver = "org.mariadb.jdbc.Driver";
		final String db_ip = "localhost";
		final String db_port = "3306";
		final String db_name = "student_test";
		final String db_url = "jdbc:mariadb://" + db_ip + ":" + db_port + "/" + db_name;
		
		//JDBC connection --> import mysql 
	    Connection conn = null;
	    //prepareStatement = 실행될 쿼리를 담을 값을 변수
	    PreparedStatement pstmt = null;
	    //resultSet = 쿼리 결과값을 담을 변수
	    ResultSet rs = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공!");
			}
		} catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
			
		} catch(SQLException e) {
			System.out.println("DB접속 실패!");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT school_id, school_name, school_area "
						+ "FROM tb_school_info ";
			
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			List<HashMap<String, Object>> list = new ArrayList();
			
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("schoolId", rs.getInt(1));
				rsMap.put("schoolName", rs.getString(2));
				rsMap.put("schoolArea", rs.getString(3));
				list.add(rsMap);

			}
			System.out.println("학교Id\t학교이름\t\t학교지역");
			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i).get("schoolId") + "\t"
								+ list.get(i).get("schoolName") + "\t"
								+ list.get(i).get("schoolArea"));
			}
			
		} catch(SQLException e) {
			System.out.println("error : " + e);
			
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
