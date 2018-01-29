package org.sterl.cloud.admin.impl.db.business;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloud.admin.api.db.DbConnectionId;
import org.sterl.cloud.admin.impl.common.model.CredentialsBE;
import org.sterl.cloud.admin.impl.db.dao.DbConnectionDao;
import org.sterl.cloud.admin.impl.db.exception.DbException.DBConnectionNotFoundException;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;
import org.sterl.cloud.admin.impl.dbdriver.business.DbDriverBM;

@Service
@Transactional(timeout = 30)
public class DbConnectionBM {
    @Autowired private DbDriverBM driverBM;
    @Autowired private SaveDbConnectionBA saveDbConnectionBA;
    @Autowired private CreateJdbcQueryExecutorBA createQueryExecutorBA;
    @Autowired private DbConnectionDao dbConnectionDao;
    
    @PostConstruct
    protected void dummyData() {
        DbConnectionBE connection = new DbConnectionBE();
        connection.setName("Local PostgreSql");
        connection.setCredentials(new CredentialsBE("postgres", "postgres"));
        connection.setSchema("public");
        connection.setDriver("org.postgresql.Driver");
        connection.setUrl("jdbc:postgresql://localhost:5432/postgres");
        save(connection);
    }
    /**
     * Saves the given {@link DbConnectionBE}.
     * @param connection the {@link DbConnectionBE} to save
     * @return the attached {@link DbConnectionBE}
     */
    public DbConnectionBE save(DbConnectionBE connection) {
        return saveDbConnectionBA.save(connection);
    }
    
    public void validateConnection(DbConnectionBE connection) {
        final SimpleDriverDataSource driver = driverBM.newDriver(connection);
        final JdbcQueryExecutor con = createQueryExecutorBA.newQueryExecutor(null, driver);
        con.connect();
        con.close();
    }

    /**
     * Returns the {@link DbConnectionBE} for the given id or an error if not found.
     * @param id the id of the connection
     * @return the found {@link DbConnectionBE}
     * @throws DBConnectionNotFoundException if not found
     */
    public DbConnectionBE getOrFail(DbConnectionId id) {
        DbConnectionBE connection = dbConnectionDao.findOne(id.getValue());
        if (connection == null) throw new DBConnectionNotFoundException(id);
        if (createQueryExecutorBA.getCachedExecutor(connection.getStrongId()) == null) {
            connection.setConnected(Boolean.FALSE);
        } else {
            connection.setConnected(Boolean.TRUE);
        }
        return connection;
    }

    public List<DbConnectionBE> getConnections() {
        List<DbConnectionBE> connections = dbConnectionDao.findAll();
        for (DbConnectionBE dbCon : connections) {
            dbCon.setConnected(
                    createQueryExecutorBA.getCachedExecutor(dbCon.getStrongId()) == null ? Boolean.FALSE : Boolean.TRUE);
        }
        return connections;
    }
    
    public JdbcQueryExecutor connect(DbConnectionId connectionId) {
        JdbcQueryExecutor result = createQueryExecutorBA.getCachedExecutor(connectionId);
        if (result == null) {
            // okay we need maybe a new one here ...
            synchronized (createQueryExecutorBA) {
                result = createQueryExecutorBA.getCachedExecutor(connectionId);
                if (result == null) {
                    // we really need a new one
                    final DbConnectionBE conData = getOrFail(connectionId);
                    final SimpleDriverDataSource driver = driverBM.newDriver(conData);
                    result = createQueryExecutorBA.newQueryExecutor(conData.getStrongId(), driver);
                }
            }
        }
        return result;
    }
    
    public void disconnect(DbConnectionId connectionId) {
        createQueryExecutorBA.closeConnection(connectionId);
    }
}