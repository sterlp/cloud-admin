package org.sterl.cloud.admin.impl.dbdriver.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sterl.cloud.admin.api.dbdriver.ValidateUrlCmd;
import org.sterl.cloud.admin.impl.dbdriver.business.DbDriverBM;

@RestController
@RequestMapping("/api/db")
public class DbBF {
    @Autowired DbDriverBM dbDriver;
    
    @RequestMapping(value = "/jdbc/drivers", method = RequestMethod.GET)
    public Set<String> getJdbcDbDrivers() {
        return dbDriver.getJdbcDrivers().keySet();
    }
    
    @RequestMapping(value = "/jdbc/drivers", method = RequestMethod.POST)
    public ResponseEntity<?> validate(@RequestBody ValidateUrlCmd validateUrlCmd) {
        boolean result;
        if (validateUrlCmd.getDriver() == null && validateUrlCmd.getDbDriver() == null) {
            throw new IllegalArgumentException("Neither SQL driver nor DB Model Driver given.");
        } else if (validateUrlCmd.getDriver() == null) {
            result = dbDriver.validateUrl(validateUrlCmd.getDbDriver(), validateUrlCmd.getUrl());
        } else {
            result = dbDriver.validateUrl(validateUrlCmd.getDriver(), validateUrlCmd.getUrl());
        }
        return ResponseEntity.ok(result);
    }
}