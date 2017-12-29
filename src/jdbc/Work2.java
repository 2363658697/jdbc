package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import Tools.DbUtils;

public class Work2 {
	/**
	 * 使用游标输出 scott中所有的雇员名称，部门名称，年薪
	 * 
	 * @throws Exception
	 */
	@Test
	public void selectScott() throws Exception {
		// 调用DbUtils类里的getConnection()方法返回一个Connection conn
		Connection conn = DbUtils.getConnection();
		// conn 调用createStatement()方法返回一个Statement state，执行静态SQL语句
		Statement state = conn.createStatement();
		// 定义String类型的变量sql写入sql语句
		String sql = "select e.ename,d.dname,e.sal from emp e inner join dept d on e.deptno=d.deptno";
		// state调用executeQuery()方法执行sql语句，变量sql作为参数传入，返回一个游标对象ResultSet rs
		ResultSet rs = state.executeQuery(sql);
		// 循环打印sql语句查询的结果，re调用next()方法判断是否读取到最后一行
		while (rs.next()) {
			// 获取sql语句中的变量，根据变量的数据类型调用对应的获取方法
			String ename = rs.getString("ename");
			String dname = rs.getString("dname");
			int yearSal = rs.getInt("sal");
			System.out.println(ename + "----" + dname + "----" + yearSal * 12);
		}
		//关闭通道
		rs.close();
		state.close();
		conn.close();
	}
	
}
