package de.vinogradoff.sql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SqlDriver {
    protected static Connection con;

    /**
     * Initialize connection using system properties:
     *      &lt;sql-db.url&gt;jdbc:sqlserver://p2012-d9019:1433&lt;/sql-db.url&gt;
     *      &lt;sql-db.user&gt;sa&lt;/sql-db.user&gt;
     *      &lt;sql-db.password&gt;#######&lt;/sql-db.password&gt;
     *
     * @throws SQLException
     */
    public static void initSQLConnection() throws SQLException{
        setConnection(new DBCPDataSource().getConnection());
    }


    /**
     * Initialize connection with given connection settings
     * @param url for example jdbc:sqlserver://p2012-d9019:1433
     * @param user
     * @param password
     * @throws SQLException
     */
    public static void initSQLConnection(String url, String user, String password) throws SQLException{
        setConnection(new DBCPDataSource(url,user,password).getConnection());
    }

    private static void setConnection(Connection connection){
        con=connection;
    }

     /**
     * counts the number of results
     * @param query must return one integer value, ? for parametes
     * @param params String, Integer or Bigdecimal
     * @return
     * @throws SQLException if 0 or more than 1 value returned.
     */
    public static int countResults(String query, Object... params) throws SQLException {
        if (params.length>1) throw new UnsupportedOperationException("Only single paramater supported now");
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            if (params.length > 0) {
                if (params[0] instanceof String) {
                    stmt.setString(1, (String) params[0]);
                } else if (params[0] instanceof Integer) {
                    stmt.setInt(1, (Integer) params[0]);
                } else if (params[0] instanceof BigDecimal) {
                    stmt.setBigDecimal(1, (BigDecimal) params[0]);
                } else throw new SQLException("type of parameter " + params[0] + " is not supported. Supported types: String, Integer, BigDecimal.");
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new SQLException("Query " + query + " returned no results");
        }
    }

    /**
     * Executes delete statement
     * @param deleteStatement
     * @param params String, Integer, BigDecimal supported
     * @return number of deleted lines
     * @throws SQLException
     */
    public static int deleteLines(String deleteStatement, Object... params) throws SQLException {
        if (params.length>1) throw new UnsupportedOperationException("Only single paramater supported now");
        try (PreparedStatement stmt = con.prepareStatement(deleteStatement)) {
            if (params.length > 0) {
                if (params[0] instanceof String) {
                    stmt.setString(1, (String) params[0]);
                } else if (params[0] instanceof Integer) {
                    stmt.setInt(1, (Integer) params[0]);
                } else if (params[0] instanceof BigDecimal) {
                    stmt.setBigDecimal(1, (BigDecimal) params[0]);
                } else throw new SQLException("type of parameter " + params[0] + " is not supported.");
            }
            return stmt.executeUpdate();
        }
    }
    /**
     * Selects list of String values
     * @param query must return one list of values, ? for parametes
     * @return
     * @throws SQLException if 0 values returned
     */

    public static List<String> getListOfValues(String query) throws SQLException{
        List<String> list=new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString(1));
                }
            }
            if (list.isEmpty()) throw new SQLException("Query " + query + " returned no results");
            else return list;
        }
    }


    /**
     * Selects a single value
     * @param query must return one value, ? for parametes
     * @param params String, Integer or Bigdecimal
     * @return
     * @throws SQLException if 0 or more than 1 value returned.
     */
    public static String getSingleValue(String query, Object... params) throws SQLException{
        if (params.length>1) throw new UnsupportedOperationException("Only single paramater supported now");
        String result;
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            if (params.length > 0) {
                if (params[0] instanceof String) {
                    stmt.setString(1, (String) params[0]);
                } else if (params[0] instanceof Integer) {
                    stmt.setInt(1, (Integer) params[0]);
                } else if (params[0] instanceof BigDecimal) {
                    stmt.setBigDecimal(1, (BigDecimal) params[0]);
                } else throw new SQLException("type of parameter " + params[0] + " is not supported. Supported types: String, Integer, BigDecimal.");
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString(1);
                } else {
                    throw new SQLException("Query " + query + " returned no results");
                }
                if (rs.next()) {
                    throw new SQLException("Query " + query + " returned more than one result, only once expected.");
                }
            }
            return result;
        }
    }

}
