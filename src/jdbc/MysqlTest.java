package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class MysqlTest {

	/**
	 * DriverManager 驱动管理类 通过获取不同数据库的驱动类 实现不同的登录方式
	 *  Connection 用于和数据库进行连接类
	 * Statement 用于执行静态sql语句 
	 * ResultSet 游标对象 抓取行
	 * @author Administrator
	 */

	/**
	 * 获取数据库连接类
	 * 
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {

		String url = "jdbc:mysql://localhost:3306/work"; // 数据库连接类型

		String driverClass = "com.mysql.jdbc.Driver"; // 驱动

		String user = "root"; // 用户名

		String password = "123456"; // 密码

		Class.forName(driverClass); // jvm加载驱动类
		
		// DriverManager 驱动管理类 调用getConnection()方法返回Connection		
		Connection conn = DriverManager.getConnection(url, user, password); 
																	
		return conn;
	}

	/**
	 * 执行数据库的查询语句
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void select() throws ClassNotFoundException, SQLException {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "select * from dept";
		//state调用executeQuery()方法执行sql语句，变量sql作为参数传入返回ResultSet 游标对象 抓取行
		ResultSet rs=state.executeQuery(sql);
		//re.next()方法判读表中数据是否读取完
		while(rs.next()){
			//获取dept表中的deptno
			double dept=rs.getDouble("deptno");
			//获取dept表中的dname
			String dname=rs.getString("dname");
			//获取dept表中的loc
			String loc=rs.getString("loc");
			//打印表中数据
			System.out.println(dept+"---"+dname+"----"+loc);
		}
		state.close();
		conn.close();
	}

	/**
	 * 执行数据库的插入语句
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void insert() throws ClassNotFoundException, SQLException {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "insert into dept values(60,'深圳','龙华')";
		// satate调用excute()方法执行sql语句,变量sql作为参数传入
		int length=state.executeUpdate(sql);
		System.out.println(length);
		state.close();
		conn.close();
	}
	/**
	 * 执行数据库的更新语句
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void update() throws ClassNotFoundException, SQLException {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "update dept set  loc='nnn' where deptno=50";
		// satate调用excute()方法执行sql语句,变量sql作为参数传入
		int length=state.executeUpdate(sql);
		System.out.println(length);
		state.close();
		conn.close();
	}

	/**
	 * 执行数据库的delete语句
	  * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void delete() throws ClassNotFoundException, SQLException {
		// 通过getConnection()方法返回一个Connection对象conn
		Connection conn = getConnection();
		// 调用conn的createStatement()方法返回 Statement对象，用于执行静态sql语句
		Statement state = conn.createStatement();
		// 用一个String类型变量sql写一条sql语句
		String sql = "delete from dept where deptno=40";
		// satate调用excute()方法执行sql语句,变量sql作为参数传入
		int length=state.executeUpdate(sql);
		System.out.println(length);
		state.close();
		conn.close();
	}

}
