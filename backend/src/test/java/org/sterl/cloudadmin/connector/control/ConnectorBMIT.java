package org.sterl.cloudadmin.connector.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;
import org.sterl.cloudadmin.connector.example.ExampleConnector;
import org.sterl.cloudadmin.connector.example.ExampleProvider;
import org.sterl.cloudadmin.connector.model.ConnectorBE;
import org.sterl.cloudadmin.system.dao.SystemDAO;
import org.sterl.cloudadmin.system.model.SystemBE;
import org.sterl.cloudadmin.system.model.SystemCredentialBE;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
@Tag("IT")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ConnectorBMIT {

    @Autowired SystemDAO systemDAO;
    @Autowired ConnectorBM service;
    @Autowired ConnectorRegistry registry;
    @Autowired TransactionTemplate trx;
    
    @BeforeAll @AfterAll
    void clean() {
        registry.stop();
        systemDAO.deleteAll();
    }
    
    @Test
    void testGetConnectors() {
        assertTrue(registry.getConnectors().size() >= 1);
        assertTrue(registry.getConnectors().contains(ExampleConnector.class));
    }
    
    @Test
    void testActivateSampleConnector() throws Exception {
        SystemBE system = new SystemBE("Example", ExampleConnector.class);
        system.addConfig(ExampleProvider.PERMISSIONS_PROP, "READ, WRITE, ADMIN");
        system.addConfig(ExampleProvider.RESOURCES_PROP, "Resource1, Resource2");
        SystemCredentialBE credential = new SystemCredentialBE("NONE");
        
        final ConnectorBE activate1 = service.activate(system, credential);

        assertEquals(3, activate1.getConnector().getAllPermissions().size());
        assertEquals(2, activate1.getConnector().getAllResources().size());
        
        trx.execute((status) -> {
            SystemBE s = systemDAO.getOne(activate1.getSystem().getId());
            assertEquals(3, s.getPermissions().size());
            assertEquals(2, s.getResources().size());
            return s;
        });
        
        system = new SystemBE("Example 2", ExampleConnector.class);
        system.addConfig(ExampleProvider.PERMISSIONS_PROP, "READ, WRITE");
        final ConnectorBE activate2 = service.activate(system, credential);

        trx.execute((status) -> {
            SystemBE s = systemDAO.getOne(activate2.getSystem().getId());
            assertEquals(2, s.getPermissions().size());
            assertEquals(0, s.getResources().size());
            return s;
        });
        
        system = new SystemBE("Example 3", ExampleConnector.class);
        final ConnectorBE activate3 = service.activate(system, credential);

        trx.execute((status) -> {
            SystemBE s = systemDAO.getOne(activate3.getSystem().getId());
            assertEquals(0, s.getPermissions().size());
            assertEquals(0, s.getResources().size());
            return s;
        });
        
        assertEquals(3, registry.getActiveConnectors().size());
        
        registry.stop();
        assertEquals(0, registry.getActiveConnectors().size());
        
        registry.init();
        assertEquals(3, registry.getActiveConnectors().size());
        trx.execute((status) -> {
            SystemBE s = systemDAO.getOne(activate1.getSystem().getId());
            assertEquals(3, s.getPermissions().size());
            assertEquals(2, s.getResources().size());
            return s;
        });
    }
    
}