package jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Tools.DbUtils;

public class Note {
	/**
	 * PreparedStatement 的用法
	 * @throws Exception
	 */
		@Test
		public void demo() throws Exception{
			Connection conn=DbUtils.getConnection();
			
			String sql="select * from emp where deptno=?";
			
			PreparedStatement ps=conn.prepareStatement(sql);
			
			//?的下标从1开始，set?的对应数据类型
			ps.setInt(1, 6666);
			
			ps.executeQuery();
		}
		/**
		 * 事务是一个运行的单元 该单元运行的程序，必须满足acid特性
		 * jdbc中事务的自动提交
		 * jdbc事务设置为手动提交，所有方法中的代码在同一个事务中
		 * @throws SQLException 
		 */
		public void demo2() throws SQLException{
			Connection conn = null;
			PreparedStatement ps=null;
			
			try {
				conn=DbUtils.getConnection();
				conn.setAutoCommit(false);  //设置手动提交事务
				String sql="select * from emp where empno=33";
				PreparedStatement p=conn.prepareStatement(sql);
				p.executeQuery();
				p.close();
				
				String sql2="select * from emp where empno=63";
				ps=conn.prepareStatement(sql2);
				ps.executeQuery();
				
				conn.commit();  //提交事务
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback(); //回滚事务
			}
			conn.close();
			ps.close();
		}
		
		/**
		 * 存储过程的使用
		 * {call 存储过程名(?,?,?)}  ?代表参数
		 * @throws Exception 
		 */
		@Test
		public void demo4() throws Exception{
			String sql="{call pro_add(?,?,?)}";
			Connection conn=DbUtils.getConnection();
			
			CallableStatement cs=conn.prepareCall(sql);
			cs.setInt(1,33);
			cs.setInt(2, 55);
			cs.registerOutParameter(3, Types.INTEGER);//接收out返回值
			cs.execute();
			
			int count=cs.getInt(3);
			System.out.println(count);
		}
		/**
		 * 函数的调用{ ?= call 函数名(?,?)}
		 * @throws Exception
		 */
		@Test
		public void demo5() throws Exception{
			String sql="{ ?=call fun_add(?,?)}";
			Connection conn=DbUtils.getConnection();
			
			CallableStatement cs=conn.prepareCall(sql);
			cs.setInt(2,33);
			cs.setInt(3, 55);
			cs.registerOutParameter(1, Types.INTEGER);//接收out返回值
			cs.execute();
			
			int count=cs.getInt(1);
			System.out.println(count);
		}
		
		
		static class User{
			String name;
			int age;
		}
		static List<User> list=new ArrayList<>();
		static{
			User user=null;
			for(int i=1;i<=100;i++){
				user=new User();
				user.name="ww"+i;
				user.age=i;
				list.add(user);
			}		
		}
		/**
		 *批量处理
		 * @throws Exception 
		 */
		public void demo6() throws Exception{
			String sql="insert into aa(name,age)  values(?,?)";
	        Connection conn=DbUtils.getConnection();
			
			PreparedStatement ps=conn.prepareStatement(sql);
			for(int i=0;i<list.size();i++){
				User user=list.get(i);
				ps.setString(1,user.name);
				ps.setInt(2, user.age);
				ps.addBatch();  //打包
			}		
			ps.executeBatch();  //执行
			ps.close();
			conn.close();
		}
		
		/**
		 * ResultSetMetaData
		 * 获取列名和打印数据行
		 * @throws Exception
		 */
		@Test
		public void test1() throws Exception {
			Connection conn = DbUtils.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from dept ");

			ResultSetMetaData metaData = resultSet.getMetaData();
			
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				// resultSet数据下标从1开始
				String columnName = metaData.getColumnName(i + 1);
				int type = metaData.getColumnType(i + 1);
				System.out.print(columnName + "\t");
			}
			System.out.println();
			// 获取数据
			while (resultSet.next()) {
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					// resultSet数据下标从1开始
					System.out.print(resultSet.getString(i + 1) + "\t");
				}
				System.out.println();
			}
			statement.close();
			conn.close();
		}
}
