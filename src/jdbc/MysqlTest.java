package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class MysqlTest {

	/**
	 * DriverManager ���������� ͨ����ȡ��ͬ���ݿ�������� ʵ�ֲ�ͬ�ĵ�¼��ʽ
	 *  Connection ���ں����ݿ����������
	 * Statement ����ִ�о�̬sql��� 
	 * ResultSet �α���� ץȡ��
	 * @author Administrator
	 */

	/**
	 * ��ȡ���ݿ�������
	 * 
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {

		String url = "jdbc:mysql://localhost:3306/work"; // ���ݿ���������

		String driverClass = "com.mysql.jdbc.Driver"; // ����

		String user = "root"; // �û���

		String password = "123456"; // ����

		Class.forName(driverClass); // jvm����������
		
		// DriverManager ���������� ����getConnection()��������Connection		
		Connection conn = DriverManager.getConnection(url, user, password); 
																	
		return conn;
	}

	/**
	 * ִ�����ݿ�Ĳ�ѯ���
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void select() throws ClassNotFoundException, SQLException {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "select * from dept";
		//state����executeQuery()����ִ��sql��䣬����sql��Ϊ�������뷵��ResultSet �α���� ץȡ��
		ResultSet rs=state.executeQuery(sql);
		//re.next()�����ж����������Ƿ��ȡ��
		while(rs.next()){
			//��ȡdept���е�deptno
			double dept=rs.getDouble("deptno");
			//��ȡdept���е�dname
			String dname=rs.getString("dname");
			//��ȡdept���е�loc
			String loc=rs.getString("loc");
			//��ӡ��������
			System.out.println(dept+"---"+dname+"----"+loc);
		}
		state.close();
		conn.close();
	}

	/**
	 * ִ�����ݿ�Ĳ������
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void insert() throws ClassNotFoundException, SQLException {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "insert into dept values(60,'����','����')";
		// satate����excute()����ִ��sql���,����sql��Ϊ��������
		int length=state.executeUpdate(sql);
		System.out.println(length);
		state.close();
		conn.close();
	}
	/**
	 * ִ�����ݿ�ĸ������
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void update() throws ClassNotFoundException, SQLException {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "update dept set  loc='nnn' where deptno=50";
		// satate����excute()����ִ��sql���,����sql��Ϊ��������
		int length=state.executeUpdate(sql);
		System.out.println(length);
		state.close();
		conn.close();
	}

	/**
	 * ִ�����ݿ��delete���
	  * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void delete() throws ClassNotFoundException, SQLException {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "delete from dept where deptno=40";
		// satate����excute()����ִ��sql���,����sql��Ϊ��������
		int length=state.executeUpdate(sql);
		System.out.println(length);
		state.close();
		conn.close();
	}

}
