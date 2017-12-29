package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import Tools.DbUtils;

/**
 * 定义一个方法 传入表名 删除该表中的重复记录
 * 
 * 比如 deleteMul(tableName)
 * 
 * 调用 deleteMul('emp'); 必须删除表emp的重复数据 （execute immediate using ）
 * 
 * @author Administrator
 *
 */
public class Work3 {
	public static void main(String[] args) throws Exception {
			Work3 w=new Work3();
			
			w.deleteMul("aa");
		}

	/**
	 * 传入表名 删除该表中的重复记录
	 * 
	 * @param tableName
	 *            传入的表名
	 * @throws Exception
	 *             需要抛出的异常
	 */
	public void deleteMul(String tableName) throws Exception {
		// 调用工具类DbUtils的getConnection()方法获取连接对象 Connection
		Connection conn = DbUtils.getConnection();
		// 定义String类型变量接收getPrimaryKey()方法返回的传入的表的主键列
		String pkName = getPrimaryKey(tableName, conn);
		// 定义String类型变量接收getColumnName()方法返回的传入的表的主键列以外的其他所有列
		String columnName = getColumnName(tableName, conn);
		// 定义sql语句，将传入的参数进行拼接
		String sql = "delete from " + tableName + " where " + pkName + " not in(select max(" + pkName + ") from "
				+ tableName + " group by " + columnName + ")";
		// 获取执行sql语句的对象Statement
		Statement state = conn.createStatement();
		// 执行sql语句并返回传入表的受影响行数
		int count = state.executeUpdate(sql);
		// 等于传入表的受影响行数
		System.out.println(count);
		//关闭通道
		state.close();
		conn.close();
	}

	/**
	 * 获取传入的表的主键列名
	 * @param tableName 传入的表名
	 * @param conn  连接数据库的对象
	 * @return String
	 * @throws SQLException
	 */
	private String getPrimaryKey(String tableName, Connection conn) throws SQLException {
		// 定义接收主键列名的变量
		String pkName = null;
		// 获取执行sql语句的对象Statement
		Statement state = conn.createStatement();
		// 定义sql语句
		String sql = "select cu.column_name from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name ='"
				+ tableName.toUpperCase() + "'";
		// 执行sql语句返回一个结果集ResultSet 游标对象，每次抓取一行
		ResultSet rs = state.executeQuery(sql);
		// next()方法判断数据是否读取完
		while (rs.next()) {
			// 获取主键列名
			pkName = rs.getString("column_name");
		}
		//关闭通道
		rs.close();
		// 返回主键列名
		return pkName;
	}

	/**
	 * 获取传入的表的除主键列之外的列名
	 * @param tableName 传入的表名
	 * @param conn   连接数据库的对象
	 * @return String
	 * @throws SQLException
	 */
	private String getColumnName(String tableName, Connection conn) throws SQLException {
		// 定义StringBuilder用来拼接列名
		StringBuilder sb = new StringBuilder();
		// 定义要执行的sql语句
		String sql = "select * from " + tableName;
		// 获取执行sql语句的Statement对象
		Statement state = conn.createStatement();
		// 执行sql语句返回ResultSet对象
		ResultSet rs = state.executeQuery(sql);
		// 获取ResultSet对象子类ResultSetMetaData
		ResultSetMetaData metaData = rs.getMetaData();
		// 定义for循环获取表的列名
		for (int i = 1; i < metaData.getColumnCount()+1; i++) {
			// rs数据下标从1开始
			String columnName = metaData.getColumnName(i);
			// sb进行拼接
			sb.append(columnName).append(",");
		}
		//关闭通道
		rs.close();
		// 返回表的列名
		return sb.substring(sb.indexOf(",") + 1, sb.length() - 1);
	}
}
