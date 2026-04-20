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
    private static volatile DataSource dataSource;

    private DataSourceSingleton() {
    }

    public static DataSource getInstance() {
        if (dataSource == null) {
            synchronized (DataSourceSingleton.class) {
                if (dataSource == null) {
                    dataSource = new SimpleDriverManagerDataSource(
                            System.getProperty("coop.jdbc.url", "jdbc:h2:mem:coopdb"),
                            System.getProperty("coop.jdbc.user", "sa"),
                            System.getProperty("coop.jdbc.password", "")
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
        public PrintWriter getLogWriter() {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) {
        }

        @Override
        public void setLoginTimeout(int seconds) {
        }

        @Override
        public int getLoginTimeout() {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            throw new SQLFeatureNotSupportedException("No soportado");
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
