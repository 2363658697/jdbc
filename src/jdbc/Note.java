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
	 * PreparedStatement ���÷�
	 * @throws Exception
	 */
		@Test
		public void demo() throws Exception{
			Connection conn=DbUtils.getConnection();
			
			String sql="select * from emp where deptno=?";
			
			PreparedStatement ps=conn.prepareStatement(sql);
			
			//?���±��1��ʼ��set?�Ķ�Ӧ��������
			ps.setInt(1, 6666);
			
			ps.executeQuery();
		}
		/**
		 * ������һ�����еĵ�Ԫ �õ�Ԫ���еĳ��򣬱�������acid����
		 * jdbc��������Զ��ύ
		 * jdbc��������Ϊ�ֶ��ύ�����з����еĴ�����ͬһ��������
		 * @throws SQLException 
		 */
		public void demo2() throws SQLException{
			Connection conn = null;
			PreparedStatement ps=null;
			
			try {
				conn=DbUtils.getConnection();
				conn.setAutoCommit(false);  //�����ֶ��ύ����
				String sql="select * from emp where empno=33";
				PreparedStatement p=conn.prepareStatement(sql);
				p.executeQuery();
				p.close();
				
				String sql2="select * from emp where empno=63";
				ps=conn.prepareStatement(sql2);
				ps.executeQuery();
				
				conn.commit();  //�ύ����
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback(); //�ع�����
			}
			conn.close();
			ps.close();
		}
		
		/**
		 * �洢���̵�ʹ��
		 * {call �洢������(?,?,?)}  ?�������
		 * @throws Exception 
		 */
		@Test
		public void demo4() throws Exception{
			String sql="{call pro_add(?,?,?)}";
			Connection conn=DbUtils.getConnection();
			
			CallableStatement cs=conn.prepareCall(sql);
			cs.setInt(1,33);
			cs.setInt(2, 55);
			cs.registerOutParameter(3, Types.INTEGER);//����out����ֵ
			cs.execute();
			
			int count=cs.getInt(3);
			System.out.println(count);
		}
		/**
		 * �����ĵ���{ ?= call ������(?,?)}
		 * @throws Exception
		 */
		@Test
		public void demo5() throws Exception{
			String sql="{ ?=call fun_add(?,?)}";
			Connection conn=DbUtils.getConnection();
			
			CallableStatement cs=conn.prepareCall(sql);
			cs.setInt(2,33);
			cs.setInt(3, 55);
			cs.registerOutParameter(1, Types.INTEGER);//����out����ֵ
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
		 *��������
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
				ps.addBatch();  //���
			}		
			ps.executeBatch();  //ִ��
			ps.close();
			conn.close();
		}
		
		/**
		 * ResultSetMetaData
		 * ��ȡ�����ʹ�ӡ������
		 * @throws Exception
		 */
		@Test
		public void test1() throws Exception {
			Connection conn = DbUtils.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from dept ");

			ResultSetMetaData metaData = resultSet.getMetaData();
			
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				// resultSet�����±��1��ʼ
				String columnName = metaData.getColumnName(i + 1);
				int type = metaData.getColumnType(i + 1);
				System.out.print(columnName + "\t");
			}
			System.out.println();
			// ��ȡ����
			while (resultSet.next()) {
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					// resultSet�����±��1��ʼ
					System.out.print(resultSet.getString(i + 1) + "\t");
				}
				System.out.println();
			}
			statement.close();
			conn.close();
		}
}
