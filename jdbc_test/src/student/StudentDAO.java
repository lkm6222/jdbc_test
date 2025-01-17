package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDAO {
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	// 1. 학생 정보 등록
	public int insertStudent(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "INSERT INTO tb_student_info(\r\n"
					+ "			 student_name, \r\n"
					+ "          student_grade, \r\n"
					+ "          student_school, \r\n"
					+ "          student_addr, \r\n"
					+ "          student_phone\r\n"
					+ ") VALUES(\r\n"
					+ "	?,?,?,?,?\r\n"
					+ ");";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paramMap.get("studentName").toString());	
			pstmt.setInt(2, Integer.parseInt(paramMap.get("studentGrade").toString()));	
			pstmt.setString(3, paramMap.get("studentSchool").toString());
			pstmt.setString(4, paramMap.get("studentAddr").toString());	
			pstmt.setString(5, paramMap.get("studentPhone").toString());
			
			
			resultChk = pstmt.executeUpdate();
			//조회만 할 때 : executeQuery();
			
		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;	
	}
	
	
	// 2. 학생 성적 등록
	public int insertScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
	
	
	
	// 3.학생 정보 수정
	public int updateStudent(HashMap<String, Object> paramMap) {
		int resultChk = 0;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "UPDATE tb_student_info \n";
			
			pstmt.setInt(1, Integer.parseInt(paramMap.get("studentIdx").toString()));
			
			int updateChoice = Integer.parseInt(paramMap.get("updateChoice").toString());
			
			switch (updateChoice) {
			case 1 :
				sql += "set student_name = ?\n";
				break;
			case 2 :
				sql += "set student_school = ?\n";
				break;
			case 3 :
				sql += "set student_grade = ?\n";
				break;
			case 4 :
				sql += "set studnet_phone = ?\n";
				break;
			case 5 :
				sql += "set studnet_addr = ?\n";
				break;
			default :
				break;
			}
			
			sql += "WHERE student_idx = ?;";			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paramMap.get("updateContents").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("studentIdx").toString()));
			
			resultChk = pstmt.executeUpdate();

		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		
		
		return resultChk;
	}
	
	
	//4. 학생 정보 삭제
	public int deleteStudent(int studentIdx) {
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "DELETE FROM tb_student_info\r\n"
					   + "WHERE student_idx = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, studentIdx);

			resultChk = pstmt.executeUpdate();

		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
	
	
	// 7. 전체 학생 조회
	public List<HashMap<String, Object>> printAllStudent(){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT student_idx,\r\n"
					+ "		student_name, \r\n"
					+ "		student_grade, \r\n"
					+ "		student_school, \r\n"
					+ "		student_addr, \r\n"
					+ "		student_phone\r\n"
					+ "FROM tb_student_info;";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();

				rsMap.put("studentIdx", rs.getString("student_idx"));
				rsMap.put("studentName", rs.getString("student_name"));
				rsMap.put("studentGrade", rs.getString("student_grade"));
				rsMap.put("studentSchool", rs.getString("student_school"));
				rsMap.put("studentAddr", rs.getString("student_addr"));
				rsMap.put("studentPhone", rs.getString("student_phone"));
				
				studentList.add(rsMap);
			}

		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
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
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return studentList;
	}
	
	
	
	// 8. 학생이름으로 학생 정보 조회
	public List<HashMap<String, Object>> printSearchStudent(String studentName){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT student_idx,\r\n"
					+ "		student_name, \r\n"
					+ "		student_grade, \r\n"
					+ "		student_school, \r\n"
					+ "		student_addr, \r\n"
					+ "		student_phone\r\n"
					+ "FROM tb_student_info\r\n"
					+ "WHERE student_name = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentName);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				
				rsMap.put("studentIdx", rs.getString("student_idx"));
				rsMap.put("studentName", rs.getString("student_name"));
				rsMap.put("studentGrade", rs.getString("student_grade"));
				rsMap.put("studentSchool", rs.getString("student_school"));
				rsMap.put("studentAddr", rs.getString("student_addr"));
				rsMap.put("studentPhone", rs.getString("student_phone"));
				
				studentList.add(rsMap);
			}

		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
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
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return studentList;
	}
	

	
	
	//5. 학생 성적 수정
	public int updateScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
	

	//6. 학생 성적 삭제
	public int deleteScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
}
