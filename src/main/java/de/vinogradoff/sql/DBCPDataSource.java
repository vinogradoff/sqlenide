package de.vinogradoff.sql;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class DBCPDataSource {

    private BasicDataSource ds = new BasicDataSource();

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public DBCPDataSource(){
        ds.setUrl(System.getProperty("sql-driver.url"));
        ds.setUsername(System.getProperty("sql-driver.user"));
        ds.setPassword(System.getProperty("sql-driver.password"));
        setupSettings();
    }

    private void setupSettings() {
        ds.setMinIdle(Integer.valueOf(System.getProperty("sql-driver.min.idle", "5")));
        ds.setMaxIdle(Integer.valueOf(System.getProperty("sql-driver.max.idle", "10")));
        ds.setMaxOpenPreparedStatements(Integer.valueOf(System.getProperty("sql-driver.max.open.prepared.stmt", "100")));
    }

    public DBCPDataSource(String url, String user, String password){
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        setupSettings();
    }
}