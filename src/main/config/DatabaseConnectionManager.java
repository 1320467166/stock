package main.config;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnectionManager {
    private static final String JDBC_URL = "jdbc:mysql://106.15.184.14:3307/stock";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";

    private static BasicDataSource dataSource;

    static {
        // 初始化数据库连接池
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(JDBC_PASSWORD);

        // 设置连接池属性
        dataSource.setInitialSize(5); // 初始化连接数
        dataSource.setMaxTotal(20);   // 最大连接数
        dataSource.setMaxIdle(10);    // 最大空闲连接数
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }
}
