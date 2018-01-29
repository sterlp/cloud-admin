package org.sterl.cloud.admin.impl.db.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloud.admin.impl.db.dao.DbConnectionDao;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;
import org.sterl.cloud.admin.impl.dbdriver.business.DbDriverBM;
import org.sterl.cloud.admin.impl.dbdriver.model.DbDriver;

@Component
@Transactional(propagation = Propagation.MANDATORY)
class SaveDbConnectionBA {
    @Autowired DbDriverBM driverBM;
    @Autowired DbConnectionDao dbConnectionDao;

    DbConnectionBE save(DbConnectionBE connection) {

        final Optional<DbDriver> dbDriver = driverBM.getDbDriver(connection.getDbDriverId());
        if (dbDriver.isPresent()) {
            connection.setDriver(dbDriver.get().getDriver().getClass().getName());
        } else {
            connection.setDbDriverId(null);
        }

        return dbConnectionDao.save(connection);
    }
}