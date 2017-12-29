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
	// mysql -u用户名 -p密码 -h主机名 -p端口 数据库名

	public static void main(String[] args) throws Exception {
		String database = "mysql"; // mysql命令
		String driverClass = "com.mysql.jdbc.Driver"; // 驱动
		String user = "root"; // 用户名
		String host = "localhost"; // 主机名
		String port = "3306"; // 端口
		String password = "123456"; // 密码
		String dbName = "work"; // 数据库名
		Class.forName(driverClass); // jvm加载驱动类
		String url = "jdbc:" + database + "://" + host + ":" + port + "/" + dbName; // 路径

		System.out.println("Microsoft Windows[版本6.17601]");
		System.out.println("版本所有(c)2009 Mircosoft Corporation.保留所有权利");
		System.out.println();
		System.out.print("C：\\User\\Administor>");

		Scanner sc = new Scanner(System.in);

		while (true) {
			String content = sc.nextLine();
			content = content.replaceAll(" +", " ").trim();
			// 将输入的登录信息进行分割
			String[] command = content.split(" ");
			// 将输入的值赋值到对应的接收变量
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
			// 当输入登录信息错误是抛出异常，给出提示
			try {
				url = "jdbc:" + database + "://" + host + ":" + port + "/" + dbName; // 接收传入的参数，修改原有路径
				conn = DriverManager.getConnection(url, user, password);
				break;
			} catch (Exception e) {
				System.out.println("输入的登录信息错误");
				System.out.print("C:\\User\\Administor>");
			}
			//输入exit，quit退出程序
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
					dbName = sql.split(" ")[1]; // 获取输入的数据库名
					url = "jdbc:" + database + "://" + host + ":" + port + "/" + dbName; // 覆盖原有的路径
					conn = DriverManager.getConnection(url, user, password); // 重新连接数据库
				} catch (Exception e) {
					System.out.println("输入的数据库名:" + dbName + "错误");
				}
			}
			if (sql.startsWith("exit") || sql.startsWith("quit")) {
				conn.close();
				System.exit(0); // 退出程序
			}
		}
	}

	/**
	 * 执行sql查询语句
	 * 
	 * @param conn
	 *            连接数据库的对象
	 * @param sql
	 *            sql语句
	 */
	public static void select(Connection conn, String sql) {
		// 声明执行sql语句的对象
		Statement state = null;
		try {
			state = conn.createStatement();
			// 执行sql语句返回ResultSet
			ResultSet rs = state.executeQuery(sql);
			// ResultSet调用getMetaData()获取子类ResultSetMetaData
			ResultSetMetaData metaData = rs.getMetaData();
			// for循环打印sql语句查询出的列名
			for (int i = 1; i < metaData.getColumnCount()+1; i++) {
				// getColumnName()获取列名
				String columnName = metaData.getColumnName(i);
				System.out.print(columnName + "\t");
			}
			System.out.println();
			// 逐行获取sql语句中的数据行
			while (rs.next()) {
				// for循环打印数据化的数据
				for (int i = 1; i < metaData.getColumnCount()+1; i++) {
					// getString()方法获取数据
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("输入的命令或sql语句错误: " + sql);
		} finally {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行sql插入，修改，删除语句
	 * 
	 * @param conn
	 *            数据类连接对象
	 * @param sql
	 *            sql语句
	 */
	public static void update(Connection conn, String sql) {
		// 声明执行sql语句的对象
		Statement state = null;
		try {
			state = conn.createStatement();
			// executeUpdate()返回执行sql后受影响的数据行
			int length = state.executeUpdate(sql);
			String result = "Query OK, " + length + " row affected";
			System.out.println(result);
			state.close();
		} catch (SQLException e) {
			System.out.println("输入的命令或sql语句错误");
		} finally {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
