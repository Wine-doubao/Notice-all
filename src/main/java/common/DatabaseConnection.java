package common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseConnection {
	private static DataSource ds = null;
	private static Connection conn;
	// 初始化c3p0数据源，static表示只执行一次
	static {
		// 使用c3p0-config.xml配置文件中的named-config节点中的name属性值
		ComboPooledDataSource cpds = new ComboPooledDataSource("finalwork");
		ds = cpds;
		try {
			conn = ds.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return this.conn;
	}
	public DataSource getDataSource() {
		return this.ds;
	}
}
