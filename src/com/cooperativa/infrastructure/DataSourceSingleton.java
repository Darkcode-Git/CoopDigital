package com.cooperativa.infrastructure;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.logging.Logger;

public final class DataSourceSingleton {
    public static final String PROP_JDBC_URL = "coop.jdbc.url";
    public static final String PROP_JDBC_USER = "coop.jdbc.user";
    public static final String PROP_JDBC_PASSWORD = "coop.jdbc.password";

    private static volatile DataSource dataSource;

    private DataSourceSingleton() {
    }

    public static DataSource getInstance() {
        if (dataSource == null) {
            synchronized (DataSourceSingleton.class) {
                if (dataSource == null) {
                    dataSource = new SimpleDriverManagerDataSource(
                            System.getProperty(PROP_JDBC_URL, "jdbc:h2:mem:coopdb"),
                            System.getProperty(PROP_JDBC_USER, "sa"),
                            System.getProperty(PROP_JDBC_PASSWORD, "")
                    );
                }
            }
        }
        return dataSource;
    }

    private static final class SimpleDriverManagerDataSource implements DataSource {
        private final String url;
        private final String user;
        private final String password;

        private SimpleDriverManagerDataSource(String url, String user, String password) {
            this.url = Objects.requireNonNull(url);
            this.user = user;
            this.password = password;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url, user, password);
        }

        @Override
        public Connection getConnection(String username, String pwd) throws SQLException {
            return DriverManager.getConnection(url, username, pwd);
        }

        @Override
        public PrintWriter getLogWriter() throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException(
                    "Log writer no soportado por SimpleDriverManagerDataSource"
            );
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException(
                    "Log writer no soportado por SimpleDriverManagerDataSource"
            );
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException(
                    "Login timeout no soportado por SimpleDriverManagerDataSource"
            );
        }

        @Override
        public int getLoginTimeout() {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException(
                    "Parent logger no soportado por SimpleDriverManagerDataSource"
            );
        }

        @Override
        public <T> T unwrap(Class<T> iface) {
            throw new UnsupportedOperationException("No soportado");
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) {
            return false;
        }
    }
}
