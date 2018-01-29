package org.sterl.cloud.admin.impl.dbdriver.business;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.sterl.cloud.admin.api.db.DbConnectionId;
import org.sterl.cloud.admin.api.dbdriver.DbDriverId;
import org.sterl.cloud.admin.impl.db.business.JdbcQueryExecutor;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;
import org.sterl.cloud.admin.impl.dbdriver.model.DbDriver;

@Service
public class DbDriverBM {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcQueryExecutor.class);
    
    @Autowired private SearchDbDriverBA searchDbDriverBA;
    @Autowired private List<DbDriver> drivers;
    private Map<DbConnectionId, Optional<DbDriver>> conToDriver = new HashMap<>();
    
    public List<DbDriverId> getDbDriver() {
        return drivers.stream().map(DbDriver::getName).collect(Collectors.toList());
    }
    /**
     * Gets the driver for the given {@link DbDriverId}
     * @param driverId the {@link DbDriverId} if <code>null</code> result will be <code>null</code>
     * @return the found {@link DbDriver}, otherwise <code>null</code>
     */
    public Optional<DbDriver> getDbDriver(DbDriverId driverId) {
        if (driverId == null) return Optional.empty();
        return drivers.stream().filter(d -> d.getName().equals(driverId)).findFirst();
    }
    
    /**
     * Returns all supported drivers right now
     */
    public Map<String, Driver> getJdbcDrivers() {
        return searchDbDriverBA.execute();
    }
    /**
     * Creates a new {@link DataSource} for the given connection data
     */
    public SimpleDriverDataSource newDriver(DbConnectionBE dbConnection) {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setUrl(dbConnection.getUrl());
        ds.setUsername(dbConnection.getCredentials().getUser());
        ds.setPassword(dbConnection.getCredentials().getPassword());
        ds.setSchema(dbConnection.getSchema());
        
        Optional<DbDriver> driver = getDriver(dbConnection.getStrongId(), dbConnection.getDbDriverId());
        if (driver.isPresent()) {
            final DbDriver d = driver.get();
            ds.setDriver(d.getDriver());
            ds.setConnectionProperties(d.newConnectionProperties(dbConnection));
        } else {
            final Driver d = searchDbDriverBA.getDriver(dbConnection.getDriver());
            ds.setDriver(d);
        }
        return ds;
    }

    private Optional<DbDriver> getDriver(DbConnectionId conId, DbDriverId driverId) {
        Optional<DbDriver> result = conToDriver.get(conId);
        if (result == null) {
            result = drivers.stream().filter(d -> d.getName().equals(driverId)).findFirst();
            conToDriver.put(conId, result);
        }
        return result;
    }
    public boolean validateUrl(DbDriverId dbDriverId, String url) {
        if (dbDriverId == null) {
            throw new NullPointerException("URL cannot be validated as the given Driver ID is null. Url: " + url);
        }
        final Optional<DbDriver> dbDriver = getDbDriver(dbDriverId);
        boolean result;
        if (dbDriver.isPresent()) {
            result = validateUrl(url, dbDriver.get().getDriver());
        } else {
            throw new IllegalArgumentException("For the given ID " + dbDriverId + " no provider could be found.");
        }
        return result;
    }
    public boolean validateUrl(String driver, String url) {
        final Driver dbDriver = this.searchDbDriverBA.getDriver(driver);
        if (dbDriver == null) {
            throw new IllegalArgumentException(driver + " SQL driver couldn't be found.");
        }
        
        return validateUrl(url, dbDriver);
    }
    private boolean validateUrl(String url, final Driver dbDriver) {
        boolean result;
        try {
            result = dbDriver.acceptsURL(url);
        } catch (SQLException e) {
            result = false;
            LOG.info("Check of the URL {} failed with error {}", url, e.getMessage());
        }
        return result;
    }
}