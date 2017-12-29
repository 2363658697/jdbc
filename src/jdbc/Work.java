package jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import Tools.DbUtils;

/**
 * 1写出一个分页的方法 定义如下 tablePager(tableName,curPage,pageSize) 调用
 * tablePager('Emp',2,10) 查询emp表中 第二页的数据（每页显示10条 第二页就是 10-20条）
 * 
 * @author Administrator
 *
 */
public class Work {

	public static void main(String[] args) throws Exception {
		Work w = new Work();
		w.tablePage("emp", 2, 4);
	}

	/**
	 * 分页显示表中数据
	 * @param tableName
	 *            传入的表名
	 * @param curPage
	 *            显示表中数据的页数
	 * @param pageSize
	 *            每页显示的行数
	 * @throws Exception
	 */
	public void tablePage(String tableName, int curPage, int pageSize) throws Exception {
		// 定义显示的数据行的开始位置
		int startInx = (curPage - 1) * pageSize + 1;
		// 定义显示的数据行的结束位置
		int endInx = curPage * pageSize;
		// 获取连接数据库的对象Connection
		Connection conn = DbUtils.getConnection();
		// 获取执行sql语句的对象Statement
		Statement state = conn.createStatement();
		// 定义执行的sql语句
		String sql = "select * from (select r.*,rownum rn from " + tableName + " r) g where rn>=" + startInx
				+ " and rn<=" + endInx;
		// Statement执行sql语句返回游标对象ResultSet
		ResultSet rs = state.executeQuery(sql);
		// 获取游标对象ResultSet的子类ResultSetMetaData
		ResultSetMetaData metaData = rs.getMetaData();
		// for循环输出传入表的列名，getColumnCount()方法放回列名的个数
		for (int i = 1; i < metaData.getColumnCount(); i++) {
			// rs数据下标从1开始，getColumnName()返回列名
			String columnName = metaData.getColumnName(i);
			// 打印列名，加入空格
			System.out.print(columnName + "\t");
		}
		// 输出换行
		System.out.println();
		// 获取数据,next()方法判断数据是否读取完
		while (rs.next()) {
			for (int i = 1; i < metaData.getColumnCount(); i++) {
				// rs数据下标从1开始，根据下标获取对应列的数据
				System.out.print(rs.getString(i) + "\t");
			}
			System.out.println();
		}	
		//关闭连接对象
		rs.close();
		state.close();
		conn.close();		
	}

}
