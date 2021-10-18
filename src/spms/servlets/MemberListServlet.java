package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//DB ����
		Connection conn = null;
		//sql�� 
		Statement stmt = null;
		//sql�� ���
		ResultSet rs = null;
		
		String sqlSelect = "SELECT * FROM MEMBERS ORDER BY MNO ASC";
		
		ServletContext sc = this.getServletContext();
		
		//mysql ���� ��������
		String driver = sc.getInitParameter("driver");
		String mySqlUrl = sc.getInitParameter("url");
		String id = sc.getInitParameter("username");
		String pwd = sc.getInitParameter("password");
		
		try {
			// 1. MySQL ���� ��ü�� �ε�
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName(driver);
			// 2. MySQL ����
			conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			// 3. sql�� ��ü ����
			stmt = conn.createStatement();
			// 4. sql ���� �� ��� �� ���Ϲޱ�
			rs = stmt.executeQuery(sqlSelect);
			// 5. ����� �������� ����
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>ȸ�����</title></head>");
			out.println("<body><h1>ȸ�� ���</h1>");
			/*
			 *     /add : http://localhost:9999/add 
			 * 	   add  : http://localhost:9999/member/add 
			 * 
			 * */
			out.println("<p><a href='add'>�ű� ȸ��</a></p>");
			while(rs.next()) {
				out.println(rs.getInt(1) + ", " +
							"<a href='update?no=" + rs.getInt("MNO") + "'>" +
							rs.getString(2) + "</a>, " +
							rs.getString(3) + ", " +
							rs.getString(4) + "<br>");
			}
			out.println("</body></html>");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}