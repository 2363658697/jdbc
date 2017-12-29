package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import Tools.DbUtils;

public class Work2 {
	/**
	 * ʹ���α���� scott�����еĹ�Ա���ƣ��������ƣ���н
	 * 
	 * @throws Exception
	 */
	@Test
	public void selectScott() throws Exception {
		// ����DbUtils�����getConnection()��������һ��Connection conn
		Connection conn = DbUtils.getConnection();
		// conn ����createStatement()��������һ��Statement state��ִ�о�̬SQL���
		Statement state = conn.createStatement();
		// ����String���͵ı���sqlд��sql���
		String sql = "select e.ename,d.dname,e.sal from emp e inner join dept d on e.deptno=d.deptno";
		// state����executeQuery()����ִ��sql��䣬����sql��Ϊ�������룬����һ���α����ResultSet rs
		ResultSet rs = state.executeQuery(sql);
		// ѭ����ӡsql����ѯ�Ľ����re����next()�����ж��Ƿ��ȡ�����һ��
		while (rs.next()) {
			// ��ȡsql����еı��������ݱ������������͵��ö�Ӧ�Ļ�ȡ����
			String ename = rs.getString("ename");
			String dname = rs.getString("dname");
			int yearSal = rs.getInt("sal");
			System.out.println(ename + "----" + dname + "----" + yearSal * 12);
		}
		//�ر�ͨ��
		rs.close();
		state.close();
		conn.close();
	}
	
}
