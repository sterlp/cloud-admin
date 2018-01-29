package org.sterl.cloud.admin.impl.db.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;
import org.sterl.cloud.admin.api.db.DbConnectionId;

@Component
class CreateJdbcQueryExecutorBA {
    private static final Logger LOG = LoggerFactory.getLogger(CreateJdbcQueryExecutorBA.class);
    private Map<DbConnectionId, JdbcQueryExecutor> dbCons = new ConcurrentHashMap<>();
    
    /**
     * Creates a new {@link JdbcQueryExecutor}
     * @param dbConnection the connection meta data to use
     * @return the {@link JdbcQueryExecutor}, never <code>null</code>
     */
    JdbcQueryExecutor newQueryExecutor(DbConnectionId conId, SimpleDriverDataSource driver) {
        JdbcQueryExecutor result = new JdbcQueryExecutor(driver);
        if (conId != null) {
            dbCons.put(conId, result);
        }
        return result;
    }

    JdbcQueryExecutor getCachedExecutor(DbConnectionId id) {
        if (id == null) return null;
        return dbCons.get(id);
    }

    boolean isConnected(DbConnectionId id) {
        return dbCons.containsKey(id);
    }

    @PreDestroy
    protected void destroy() {
        if (!dbCons.isEmpty()) {
            List<JdbcQueryExecutor> toClose = new ArrayList<>(dbCons.values());
            dbCons.clear();
            LOG.info("Closing {} still open connections.", dbCons.size());
            for (JdbcQueryExecutor qe : toClose) {
                qe.close();
            }
        }
    }

    void closeConnection(DbConnectionId connectionId) {
        final JdbcQueryExecutor con = dbCons.remove(connectionId);
        if (con != null) {
            con.close();
        }
    }
}
