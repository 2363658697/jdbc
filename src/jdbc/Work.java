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
 * 1д��һ����ҳ�ķ��� �������� tablePager(tableName,curPage,pageSize) ����
 * tablePager('Emp',2,10) ��ѯemp���� �ڶ�ҳ�����ݣ�ÿҳ��ʾ10�� �ڶ�ҳ���� 10-20����
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
	 * ��ҳ��ʾ��������
	 * @param tableName
	 *            ����ı���
	 * @param curPage
	 *            ��ʾ�������ݵ�ҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ������
	 * @throws Exception
	 */
	public void tablePage(String tableName, int curPage, int pageSize) throws Exception {
		// ������ʾ�������еĿ�ʼλ��
		int startInx = (curPage - 1) * pageSize + 1;
		// ������ʾ�������еĽ���λ��
		int endInx = curPage * pageSize;
		// ��ȡ�������ݿ�Ķ���Connection
		Connection conn = DbUtils.getConnection();
		// ��ȡִ��sql���Ķ���Statement
		Statement state = conn.createStatement();
		// ����ִ�е�sql���
		String sql = "select * from (select r.*,rownum rn from " + tableName + " r) g where rn>=" + startInx
				+ " and rn<=" + endInx;
		// Statementִ��sql��䷵���α����ResultSet
		ResultSet rs = state.executeQuery(sql);
		// ��ȡ�α����ResultSet������ResultSetMetaData
		ResultSetMetaData metaData = rs.getMetaData();
		// forѭ�����������������getColumnCount()�����Ż������ĸ���
		for (int i = 1; i < metaData.getColumnCount(); i++) {
			// rs�����±��1��ʼ��getColumnName()��������
			String columnName = metaData.getColumnName(i);
			// ��ӡ����������ո�
			System.out.print(columnName + "\t");
		}
		// �������
		System.out.println();
		// ��ȡ����,next()�����ж������Ƿ��ȡ��
		while (rs.next()) {
			for (int i = 1; i < metaData.getColumnCount(); i++) {
				// rs�����±��1��ʼ�������±��ȡ��Ӧ�е�����
				System.out.print(rs.getString(i) + "\t");
			}
			System.out.println();
		}	
		//�ر����Ӷ���
		rs.close();
		state.close();
		conn.close();		
	}

}
