package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import Tools.DbUtils;

/**
 * ����һ������ ������� ɾ���ñ��е��ظ���¼
 * 
 * ���� deleteMul(tableName)
 * 
 * ���� deleteMul('emp'); ����ɾ����emp���ظ����� ��execute immediate using ��
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
	 * ������� ɾ���ñ��е��ظ���¼
	 * 
	 * @param tableName
	 *            ����ı���
	 * @throws Exception
	 *             ��Ҫ�׳����쳣
	 */
	public void deleteMul(String tableName) throws Exception {
		// ���ù�����DbUtils��getConnection()������ȡ���Ӷ��� Connection
		Connection conn = DbUtils.getConnection();
		// ����String���ͱ�������getPrimaryKey()�������صĴ���ı��������
		String pkName = getPrimaryKey(tableName, conn);
		// ����String���ͱ�������getColumnName()�������صĴ���ı�����������������������
		String columnName = getColumnName(tableName, conn);
		// ����sql��䣬������Ĳ�������ƴ��
		String sql = "delete from " + tableName + " where " + pkName + " not in(select max(" + pkName + ") from "
				+ tableName + " group by " + columnName + ")";
		// ��ȡִ��sql���Ķ���Statement
		Statement state = conn.createStatement();
		// ִ��sql��䲢���ش�������Ӱ������
		int count = state.executeUpdate(sql);
		// ���ڴ�������Ӱ������
		System.out.println(count);
		//�ر�ͨ��
		state.close();
		conn.close();
	}

	/**
	 * ��ȡ����ı����������
	 * @param tableName ����ı���
	 * @param conn  �������ݿ�Ķ���
	 * @return String
	 * @throws SQLException
	 */
	private String getPrimaryKey(String tableName, Connection conn) throws SQLException {
		// ����������������ı���
		String pkName = null;
		// ��ȡִ��sql���Ķ���Statement
		Statement state = conn.createStatement();
		// ����sql���
		String sql = "select cu.column_name from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name ='"
				+ tableName.toUpperCase() + "'";
		// ִ��sql��䷵��һ�������ResultSet �α����ÿ��ץȡһ��
		ResultSet rs = state.executeQuery(sql);
		// next()�����ж������Ƿ��ȡ��
		while (rs.next()) {
			// ��ȡ��������
			pkName = rs.getString("column_name");
		}
		//�ر�ͨ��
		rs.close();
		// ������������
		return pkName;
	}

	/**
	 * ��ȡ����ı�ĳ�������֮�������
	 * @param tableName ����ı���
	 * @param conn   �������ݿ�Ķ���
	 * @return String
	 * @throws SQLException
	 */
	private String getColumnName(String tableName, Connection conn) throws SQLException {
		// ����StringBuilder����ƴ������
		StringBuilder sb = new StringBuilder();
		// ����Ҫִ�е�sql���
		String sql = "select * from " + tableName;
		// ��ȡִ��sql����Statement����
		Statement state = conn.createStatement();
		// ִ��sql��䷵��ResultSet����
		ResultSet rs = state.executeQuery(sql);
		// ��ȡResultSet��������ResultSetMetaData
		ResultSetMetaData metaData = rs.getMetaData();
		// ����forѭ����ȡ�������
		for (int i = 1; i < metaData.getColumnCount()+1; i++) {
			// rs�����±��1��ʼ
			String columnName = metaData.getColumnName(i);
			// sb����ƴ��
			sb.append(columnName).append(",");
		}
		//�ر�ͨ��
		rs.close();
		// ���ر������
		return sb.substring(sb.indexOf(",") + 1, sb.length() - 1);
	}
}
