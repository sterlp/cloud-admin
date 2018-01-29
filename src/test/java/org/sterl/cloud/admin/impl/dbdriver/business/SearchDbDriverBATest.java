package org.sterl.cloud.admin.impl.dbdriver.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Driver;
import java.util.Map;

import org.junit.Test;
import org.sterl.cloud.admin.impl.dbdriver.business.SearchDbDriverBA;

public class SearchDbDriverBATest {

    private SearchDbDriverBA searchDbDriverBA = new SearchDbDriverBA();

    @Test
    public void testSearchDBDriver() {
        Map<String, Driver> drivers = searchDbDriverBA.execute();
        assertTrue("Missing org.postgresql.Driver in " + drivers, drivers.keySet().contains("org.postgresql.Driver"));
        assertTrue("We should have at least two drivers HSQL and PostgreSQL: " + drivers, drivers.size() >= 2);
    }
    
    @Test
    public void testSecureDriverList() {
        int drivers = searchDbDriverBA.execute().size();
        searchDbDriverBA.execute().clear();
        assertEquals(drivers, searchDbDriverBA.execute().size());
    }
}
