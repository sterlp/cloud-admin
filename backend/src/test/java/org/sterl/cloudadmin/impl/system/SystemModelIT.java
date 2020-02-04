package org.sterl.cloudadmin.impl.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.sterl.cloudadmin.api.system.ExternalAccountId.newExternalAccountId;
import static org.sterl.cloudadmin.api.system.ExternalPermissionId.newExternalPermissionId;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.api.system.SystemCredentialType;
import org.sterl.cloudadmin.impl.system.dao.SystemAccountDAO;
import org.sterl.cloudadmin.impl.system.dao.SystemCredentialDAO;
import org.sterl.cloudadmin.impl.system.dao.SystemDAO;
import org.sterl.cloudadmin.impl.system.dao.SystemPermissionDAO;
import org.sterl.cloudadmin.impl.system.dao.SystemResourceDAO;
import org.sterl.cloudadmin.impl.system.model.SystemAccountBE;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemCredentialBE;
import org.sterl.cloudadmin.impl.system.model.SystemPermissionBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Tag("IT")
@TestInstance(Lifecycle.PER_CLASS)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
@Transactional(propagation= Propagation.NOT_SUPPORTED)
public class SystemModelIT {

    @Autowired SystemCredentialDAO credentialDAO;
    @Autowired SystemDAO systemDAO;
    @Autowired SystemResourceDAO resourceDAO;
    @Autowired SystemPermissionDAO permissionDAO;
    @Autowired SystemAccountDAO accountDAO;
    @Autowired EntityManager em;
    
    SystemCredentialBE c;
    SystemBE s;
    
    @BeforeAll @AfterAll
    void before() {
        systemDAO.deleteAll();
        credentialDAO.deleteAll();
    }
    
    @Test
    @Order(1)
    @DisplayName("System with Credential")
    void testCreateSystemWithCredential() {
        c = credentialDAO.save(new SystemCredentialBE()
            .setName("Test Credential")
            .setType(SystemCredentialType.BASIC)
            .setUser("sa")
        );
        
        assertNotNull(c.getId());
        assertFalse(TestTransaction.isActive());
        s = systemDAO.save(new SystemBE()
            .addConfig("foo", "bar")
            .setClassName("foo")
            .setName("Test System")
            .setCredential(em.getReference(SystemCredentialBE.class, c.getId()))
        );
        assertFalse(TestTransaction.isActive());
        assertNotNull(s.getId());
        assertEquals(c.getId(), s.getCredential().getId());
        
        systemDAO.save(new SystemBE()
            .addConfig("foo", "bar")
            .setClassName("foo")
            .setName("Test System 2")
            .setCredential(em.getReference(SystemCredentialBE.class, c.getId()))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Permission & Resources")
    void testAddPermissionsAndResources() {
        assertNotNull(s);

        final SystemBE systemRef = em.getReference(SystemBE.class, s.getId());
        final List<SystemResourceBE> resources = resourceDAO.saveAll(Arrays.asList(
                new SystemResourceBE(systemRef, "CUSTOMER", "TABLE"),
                new SystemResourceBE(systemRef, "CUSTOMER", "VIEW")
            )
        );
        assertNotNull(resources);
        assertNotNull(resources.get(0));
        resources.forEach(r -> assertNotNull(r.getId(), "ID should be generated but is null"));
        assertThrows(DataIntegrityViolationException.class, 
                () -> resourceDAO.save(new SystemResourceBE(systemRef, "CUSTOMER", "TABLE")));
        
        final List<SystemPermissionBE> permissions = permissionDAO.saveAll(Arrays.asList(
                new SystemPermissionBE().setName(newExternalPermissionId("READ")).setSystem(systemRef),
                new SystemPermissionBE().setName(newExternalPermissionId("WRITE")).setSystem(systemRef),
                new SystemPermissionBE().setName(newExternalPermissionId("ADMIN")).setSystem(systemRef)
            )
        );
        assertNotNull(permissions);
        permissions.forEach(p -> assertNotNull(p.getId(), "ID should be generated but is null"));
        assertThrows(DataIntegrityViolationException.class, 
                () -> permissionDAO.save(new SystemPermissionBE().setName(newExternalPermissionId("READ")).setSystem(systemRef)));
    }
    
    @Test
    @Order(3)
    void testAccounts() {
        final SystemBE systemRef = em.getReference(SystemBE.class, s.getId());
        
        List<SystemAccountBE> accounts = accountDAO.saveAll(Arrays.asList(
                new SystemAccountBE().setName(newExternalAccountId("paul")).setSystem(systemRef),
                new SystemAccountBE().setName(newExternalAccountId("admin")).setSystem(systemRef),
                new SystemAccountBE().setName(newExternalAccountId("guest")).setSystem(systemRef),
                new SystemAccountBE().setName(newExternalAccountId("foo")).setSystem(systemRef)
            )
        );
        assertNotNull(accounts);
        accounts.forEach(a -> {
            assertNotNull(a.getId());
            assertNull(a.getIdentity());
            assertNotNull(a.getSystem().getId());
        });
        
        final SystemBE system2 = systemDAO.save(new SystemBE()
            .setClassName("bar")
            .setName("System 2")
            .setCredential(em.getReference(SystemCredentialBE.class, c.getId()))
        );
        // be sure we can save one more time the same accounts
        accountDAO.saveAll(Arrays.asList(
                new SystemAccountBE().setName(newExternalAccountId("paul")).setSystem(system2),
                new SystemAccountBE().setName(newExternalAccountId("admin")).setSystem(system2),
                new SystemAccountBE().setName(newExternalAccountId("guest")).setSystem(system2),
                new SystemAccountBE().setName(newExternalAccountId("foo")).setSystem(system2)
            )
        );
        
        assertThrows(DataIntegrityViolationException.class, 
                () -> accountDAO.save(new SystemAccountBE().setName(newExternalAccountId("paul")).setSystem(system2)));
        
        System.out.println(accountDAO.findByNameAndSystemId(newExternalAccountId("paul"), system2.getId()));
    }

    @Test
    @Order(98)
    @Transactional
    void testReadSystem() {
        SystemBE system = systemDAO.getOne(s.getId());
        assertNotNull(system.getCredential());
        assertEquals(2, system.getResources().size());
        assertEquals(3, system.getPermissions().size());
        assertEquals(4, system.getAccounts().size());
        
        final Page<SystemResourceBE> resourcePage = resourceDAO.findBySystemId(s.getId(), PageRequest.of(0, 1));
        assertEquals(2, resourcePage.getTotalElements());
        assertEquals(1, resourcePage.getNumberOfElements());
        
        final Page<SystemPermissionBE> permissionPage = permissionDAO.findBySystemId(s.getId(), PageRequest.of(0, 1));
        assertEquals(3, permissionPage.getTotalElements());
        assertEquals(1, permissionPage.getNumberOfElements());
    }
    
    @Test
    @Order(99)
    @DisplayName("Check delete order")
    void testDeleteAll() {
        systemDAO.deleteAll();
        assertEquals(0, resourceDAO.count());
    }
}
