package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MysqlClient {

	public static Connection conn;
	// mysql -u�û��� -p���� -h������ -p�˿� ���ݿ���

	public static void main(String[] args) throws Exception {
		String database = "mysql"; // mysql����
		String driverClass = "com.mysql.jdbc.Driver"; // ����
		String user = "root"; // �û���
		String host = "localhost"; // ������
		String port = "3306"; // �˿�
		String password = "123456"; // ����
		String dbName = "work"; // ���ݿ���
		Class.forName(driverClass); // jvm����������
		String url = "jdbc:" + database + "://" + host + ":" + port + "/" + dbName; // ·��

		System.out.println("Microsoft Windows[�汾6.17601]");
		System.out.println("�汾����(c)2009 Mircosoft Corporation.��������Ȩ��");
		System.out.println();
		System.out.print("C��\\User\\Administor>");

		Scanner sc = new Scanner(System.in);

		while (true) {
			String content = sc.nextLine();
			content = content.replaceAll(" +", " ").trim();
			// ������ĵ�¼��Ϣ���зָ�
			String[] command = content.split(" ");
			// �������ֵ��ֵ����Ӧ�Ľ��ձ���
			for (int i = 0; i < command.length; i++) {
				if (command[i].equals("mysql")) {
					database = command[i];
				}
				if (command[i].equals("-u")) {
					user = command[i + 1];
				}
				if (command[i].equals("-p")) {
					password = command[i + 1];
				}
				if (command[i].equals("-h")) {
					host = command[i + 1];
				}
				if (command[i].equals("-P")) {
					port = command[i + 1];
					dbName = command[i + 2];
				}
			}
			// �������¼��Ϣ�������׳��쳣��������ʾ
			try {
				url = "jdbc:" + database + "://" + host + ":" + port + "/" + dbName; // ���մ���Ĳ������޸�ԭ��·��
				conn = DriverManager.getConnection(url, user, password);
				break;
			} catch (Exception e) {
				System.out.println("����ĵ�¼��Ϣ����");
				System.out.print("C:\\User\\Administor>");
			}
			//����exit��quit�˳�����
			if(command.equals("exit") || command.equals("quit")){
				System.exit(0);
			}
		}

		while (true) {
			System.out.print(database + ">");
			String sql = sc.nextLine();
			sql = sql.replaceAll(" +", " ").trim();

			if (sql.startsWith("select ") || sql.startsWith("show ") || sql.startsWith("desc ")) {
				select(conn, sql);
			}
			if (sql.startsWith("update ") || sql.startsWith("insert ") || sql.startsWith("delete ")) {
				update(conn, sql);
			}
			if (sql.startsWith("use ")) {
				try {
					dbName = sql.split(" ")[1]; // ��ȡ��������ݿ���
					url = "jdbc:" + database + "://" + host + ":" + port + "/" + dbName; // ����ԭ�е�·��
					conn = DriverManager.getConnection(url, user, password); // �����������ݿ�
				} catch (Exception e) {
					System.out.println("��������ݿ���:" + dbName + "����");
				}
			}
			if (sql.startsWith("exit") || sql.startsWith("quit")) {
				conn.close();
				System.exit(0); // �˳�����
			}
		}
	}

	/**
	 * ִ��sql��ѯ���
	 * 
	 * @param conn
	 *            �������ݿ�Ķ���
	 * @param sql
	 *            sql���
	 */
	public static void select(Connection conn, String sql) {
		// ����ִ��sql���Ķ���
		Statement state = null;
		try {
			state = conn.createStatement();
			// ִ��sql��䷵��ResultSet
			ResultSet rs = state.executeQuery(sql);
			// ResultSet����getMetaData()��ȡ����ResultSetMetaData
			ResultSetMetaData metaData = rs.getMetaData();
			// forѭ����ӡsql����ѯ��������
			for (int i = 1; i < metaData.getColumnCount()+1; i++) {
				// getColumnName()��ȡ����
				String columnName = metaData.getColumnName(i);
				System.out.print(columnName + "\t");
			}
			System.out.println();
			// ���л�ȡsql����е�������
			while (rs.next()) {
				// forѭ����ӡ���ݻ�������
				for (int i = 1; i < metaData.getColumnCount()+1; i++) {
					// getString()������ȡ����
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("����������sql������: " + sql);
		} finally {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ִ��sql���룬�޸ģ�ɾ�����
	 * 
	 * @param conn
	 *            ���������Ӷ���
	 * @param sql
	 *            sql���
	 */
	public static void update(Connection conn, String sql) {
		// ����ִ��sql���Ķ���
		Statement state = null;
		try {
			state = conn.createStatement();
			// executeUpdate()����ִ��sql����Ӱ���������
			int length = state.executeUpdate(sql);
			String result = "Query OK, " + length + " row affected";
			System.out.println(result);
			state.close();
		} catch (SQLException e) {
			System.out.println("����������sql������");
		} finally {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
