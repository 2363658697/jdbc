package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import Tools.DbUtils;

public class OrclTest {

	/**
	 * DriverManager ���������� ͨ����ȡ��ͬ���ݿ�������� ʵ�ֲ�ͬ�ĵ�¼��ʽ
	 *  Connection ���ں����ݿ����������
	 * Statement ����ִ�о�̬sql��� 
	 * ResultSet �α���� ץȡ��
	 * @author Administrator
	 */


	/**
	 * ִ�����ݿ�Ĳ�ѯ���
	 * @throws Exception 
	 */
	@Test
	public void select() throws Exception {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = DbUtils.getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "select * from emp";
		//state����executeQuery()����ִ��sql��䣬����sql��Ϊ�������뷵��ResultSet �α���� ץȡ��
		ResultSet rs=state.executeQuery(sql);
		//re.next()�����ж����������Ƿ��ȡ��
		while(rs.next()){
			//��ȡemp���е�empno
			double dept=rs.getDouble("empno");
			//��ȡemp���е�ename
			String dname=rs.getString("ename");
			//��ȡemp���е�sal
			double sal=rs.getDouble("sal");
			//��ӡ��������
			System.out.println(dept+"---"+dname+"----"+sal);
		}		
		
		state.close();
		conn.close();
	}

	/**
	 * ִ�����ݿ�Ĳ������
	 * @throws Exception 
	 */
	@Test
	public void insert() throws Exception {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = DbUtils.getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "insert into emp(empno,ename,sal)  values(7777,'liubi',8888)";
		// satate����excute()����ִ��sql���,����sql��Ϊ��������
		int length=state.executeUpdate(sql);
		System.out.println(length);
		
		state.close();
		conn.close();
	}
	/**
	 * ִ�����ݿ�ĸ������
	 * @throws Exception 
	 */
	@Test
	public void update() throws Exception {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = DbUtils.getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "update  emp set sal=6666 where empno=7777";
		// satate����excute()����ִ��sql���,����sql��Ϊ��������
		int length=state.executeUpdate(sql);
		System.out.println(length);
		
		state.close();
		conn.close();
	}

	/**
	 * ִ�����ݿ��delete���
	 * @throws Exception 
	 */
	@Test
	public void delete() throws Exception {
		// ͨ��getConnection()��������һ��Connection����conn
		Connection conn = DbUtils.getConnection();
		// ����conn��createStatement()�������� Statement��������ִ�о�̬sql���
		Statement state = conn.createStatement();
		// ��һ��String���ͱ���sqlдһ��sql���
		String sql = "delete from emp where empno=7777";
		// satate����excute()����ִ��sql���,����sql��Ϊ��������
		int length=state.executeUpdate(sql);
		System.out.println(length);
		
		state.close();
		conn.close();
	}
}
