package org.sterl.cloud.admin.impl.db.business;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE.CommitType;
import org.sterl.cloud.admin.impl.dbquery.model.QueryResultBE;

import lombok.RequiredArgsConstructor;

/**
 * Wrapper class to execute queries like JDBC Template.
 */
@RequiredArgsConstructor
public class JdbcQueryExecutor implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcQueryExecutor.class);
    private final SimpleDriverDataSource dataSource;
    private Connection connection;
    
    public synchronized void connect() {
        try {
            try (Statement stm = getConnection().createStatement()) {
                getConnection().rollback();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Failed to connect to " + dataSource.getUrl() 
                + " with user: '" + dataSource.getUsername()
                + "' and " + (dataSource.getPassword() == null ? "no password. " : "with password. ")
                + e.getMessage(), e);
        }
    }

    public synchronized QueryResultBE query(QueryCommandBE query) {
        Connection connection;
        QueryResultBE result = new QueryResultBE(query.getQuery());
        final long startTime = System.currentTimeMillis();
        try {
            connection = getConnection();
            connection.rollback();
            if (query.getCommitType() == CommitType.COMMIT) {
                connection.setReadOnly(false);
            } else {
                connection.setReadOnly(true);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Failed to create connection. " + e.getMessage(), e);
        }
        
        try (Statement stm = connection.createStatement()) {
            // TODO: stm.setQueryTimeout(seconds); 
            if (query.getFetchCount() > 0) {
                stm.setFetchSize(query.getFetchCount());
                stm.setMaxRows(query.getFetchCount());
            }
            final boolean executeResult = stm.execute(query.getQuery());
            if (executeResult) {
                try (ResultSet rs = stm.getResultSet()) {
                    final ResultSetMetaData rsmd = rs.getMetaData();
                    final int columnCount = rsmd.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        result.addHeader(rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
                    }
                    while (rs.next()) {
                        List<Object> row = new ArrayList<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.add(rs.getString(i));
                        }
                        result.addRow(row);
                    }
                }
            } else {
                int updateCount = stm.getUpdateCount();
                if (updateCount >= 0) {
                    result.addHeader("count", "numeric");
                    result.addRow(Arrays.asList(updateCount));
                }
            }
            if (query.getCommitType() == CommitType.COMMIT) {
                connection.commit();
            } else {
                connection.rollback();
            }
            result.setQueryTime(System.currentTimeMillis() - startTime);
            LOG.debug("Query command {} received and executed in {} ms.", query, result.getQueryTime());
            // TODO: stm.getWarnings()
            return result;
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            throw new RuntimeException("Failed to execute query. " + e.getMessage(), e);
        }
    }
    /**
     * Closes the connection.
     */
    public void close() {
        if (connection != null) {
            JdbcUtils.closeConnection(connection);
            connection = null;
        }
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        }
        return connection;
    }
}
