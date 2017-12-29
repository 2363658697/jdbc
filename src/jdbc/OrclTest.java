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
	 * DriverManager 驱动管理类 通过获取不同数据库的驱动类 实现不同的登录方式
	 *  Connection 用于和数据库进行连接类
	 * Statement 用于执行静态sql语句 
	 * ResultSet 游标对象 抓取行
	 * @author Administrator
	 */


	/**
	 * 执行数据库的查询语句
	 * @throws Exception 
	 */
	@Test
	public void select() throws Exception {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = DbUtils.getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "select * from emp";
		//state调用executeQuery()方法执行sql语句，变量sql作为参数传入返回ResultSet 游标对象 抓取行
		ResultSet rs=state.executeQuery(sql);
		//re.next()方法判读表中数据是否读取完
		while(rs.next()){
			//获取emp表中的empno
			double dept=rs.getDouble("empno");
			//获取emp表中的ename
			String dname=rs.getString("ename");
			//获取emp表中的sal
			double sal=rs.getDouble("sal");
			//打印表中数据
			System.out.println(dept+"---"+dname+"----"+sal);
		}		
		
		state.close();
		conn.close();
	}

	/**
	 * 执行数据库的插入语句
	 * @throws Exception 
	 */
	@Test
	public void insert() throws Exception {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = DbUtils.getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "insert into emp(empno,ename,sal)  values(7777,'liubi',8888)";
		// satate调用excute()方法执行sql语句,变量sql作为参数传入
		int length=state.executeUpdate(sql);
		System.out.println(length);
		
		state.close();
		conn.close();
	}
	/**
	 * 执行数据库的更新语句
	 * @throws Exception 
	 */
	@Test
	public void update() throws Exception {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = DbUtils.getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "update  emp set sal=6666 where empno=7777";
		// satate调用excute()方法执行sql语句,变量sql作为参数传入
		int length=state.executeUpdate(sql);
		System.out.println(length);
		
		state.close();
		conn.close();
	}

	/**
	 * 执行数据库的delete语句
	 * @throws Exception 
	 */
	@Test
	public void delete() throws Exception {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = DbUtils.getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "delete from emp where empno=7777";
		// satate调用excute()方法执行sql语句,变量sql作为参数传入
		int length=state.executeUpdate(sql);
		System.out.println(length);
		
		state.close();
		conn.close();
	}
}
