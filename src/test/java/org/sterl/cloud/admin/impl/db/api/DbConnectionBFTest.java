package org.sterl.cloud.admin.impl.db.api;

import static org.junit.Assert.*;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.sterl.cloud.admin.api.db.DbConnectionId;
import org.sterl.cloud.admin.api.db.DbConnectionInfo;
import org.sterl.cloud.admin.api.db.UpdateDbConnectionCmd;
import org.sterl.cloud.admin.impl.db.api.DbConnectionBF;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryResultBE;
import org.sterl.cloud.admin.impl.dbquery.model.QueryCommandBE.CommitType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class DbConnectionBFTest {

    @Autowired DbConnectionBF connectionBF;
    
    @Test
    public void testCreateAndGet() {
        UpdateDbConnectionCmd cmd = new UpdateDbConnectionCmd();
        cmd.setDriver(JDBCDriver.class.getName());
        cmd.setUrl("jdbc:hsqldb:mem:testDb");
        cmd.setUser("sa");
        cmd.setName("Test DB");

        final ResponseEntity<?> create = connectionBF.create(cmd);
        assertNotNull("Missing id in header", create.getHeaders().get("id").get(0));
        
        
        final DbConnectionInfo loaded = connectionBF.get(new DbConnectionId(Long.valueOf(create.getHeaders().get("id").get(0))));
        assertFalse(loaded.getConnected());
        assertFalse(loaded.isHasPassword());
        assertEquals(cmd.getName(), loaded.getName());
        assertEquals(cmd.getUrl(), loaded.getUrl());
        assertEquals(cmd.getUser(), loaded.getUser());
        System.out.println(loaded);
    }
    @Test
    public void testCreateAndConnect() {
        UpdateDbConnectionCmd cmd = new UpdateDbConnectionCmd();
        cmd.setDriver(JDBCDriver.class.getName());
        cmd.setUrl("jdbc:hsqldb:mem:testDb");
        cmd.setUser("sa");
        cmd.setName("Test DB");

        final ResponseEntity<?> create = connectionBF.create(cmd);
        final DbConnectionId id = new DbConnectionId(Long.valueOf(create.getHeaders().get("id").get(0)));
        final QueryResultBE qRes = connectionBF.query(id, 
            new QueryCommandBE(CommitType.COMMIT, 50, "SELECT * FROM INFORMATION_SCHEMA.TABLES"));
        
        assertTrue(qRes.getHeaders().size() > 1);
        assertTrue(qRes.getData().size() > 1);
        
        DbConnectionInfo loaded = connectionBF.get(id);
        assertTrue(loaded.getConnected());
        
        connectionBF.disconnect(id);
        loaded = connectionBF.get(id);
        assertFalse(loaded.getConnected());
    }
}
