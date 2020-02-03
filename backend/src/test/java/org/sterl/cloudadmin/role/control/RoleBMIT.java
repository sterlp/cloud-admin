package org.sterl.cloudadmin.role.control;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.connector.control.ConnectorBM;
import org.sterl.cloudadmin.connector.control.ConnectorHelper;
import org.sterl.cloudadmin.connector.example.ExampleConnector;
import org.sterl.cloudadmin.connector.example.ExampleProvider;
import org.sterl.cloudadmin.connector.exception.ConnectorException;
import org.sterl.cloudadmin.connector.model.ConnectorBE;
import org.sterl.cloudadmin.identity.control.IdentityBM;
import org.sterl.cloudadmin.identity.dao.IdentityDAO;
import org.sterl.cloudadmin.identity.model.IdentityBE;
import org.sterl.cloudadmin.provisioning.control.ProvisioningBM;
import org.sterl.cloudadmin.role.dao.RoleDAO;
import org.sterl.cloudadmin.role.model.RoleBE;
import org.sterl.cloudadmin.role.model.SystemRoleBE;
import org.sterl.cloudadmin.system.dao.SystemAccountDAO;
import org.sterl.cloudadmin.system.dao.SystemCredentialDAO;
import org.sterl.cloudadmin.system.dao.SystemDAO;
import org.sterl.cloudadmin.system.model.SystemBE;
import org.sterl.cloudadmin.system.model.SystemCredentialBE;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Tag("IT")
@TestInstance(Lifecycle.PER_CLASS)
class RoleBMIT {

    @Autowired RoleBM roleBM;
    
    @Autowired IdentityDAO identityDAO;
    @Autowired SystemAccountDAO systemAccountDAO;
    @Autowired RoleDAO roleDAO;
    @Autowired SystemDAO systemDAO;
    @Autowired SystemCredentialDAO credentialDAO;
    @Autowired ConnectorBM service;
    @Autowired TransactionTemplate trx;
    
    SystemId activeGit;
    SystemId activateConfluence;
    SystemId activeCloud;
    
    @Autowired ConnectorHelper helper;
    
    final RoleId adminId = new RoleId("ADMIN_ROLE");
    
    @AfterAll
    void clean() {
        systemAccountDAO.deleteAll();
        identityDAO.deleteAll();
        roleDAO.deleteAll();
        systemDAO.deleteAll();
        credentialDAO.deleteAll();
    }

    @BeforeAll
    void before() throws Exception {
        clean();

        SystemCredentialBE credential = trx.execute((status) -> credentialDAO.save(new SystemCredentialBE("NONE")));

        SystemBE git = new SystemBE("GIT", ExampleConnector.class);
        git.addConfig(ExampleProvider.PERMISSIONS_PROP, "READ, WRITE, ADMIN");
        git.addConfig(ExampleProvider.RESOURCES_PROP, "FooTeams:Project, Repo_Team_1:Repository, Repo_Team_2:Repository");
        activeGit = service.activate(git, credential).getSystem().getId();
        
        SystemBE confluence = new SystemBE("Confluence", ExampleConnector.class);
        confluence.addConfig(ExampleProvider.PERMISSIONS_PROP, "VIEW, COMMENT, EDIT, ADMIN");
        confluence.addConfig(ExampleProvider.RESOURCES_PROP, "Space_Team_1, SpaceTeam_2");
        activateConfluence = service.activate(confluence, credential).getSystem().getId();
        
        SystemBE cloud = new SystemBE("OpenShift", ExampleConnector.class);
        cloud.addConfig(ExampleProvider.PERMISSIONS_PROP, "MEMBER, ROOT");
        cloud.addConfig(ExampleProvider.RESOURCES_PROP, "Cloud_Team_1, Cloud_Team_2");
        activeCloud = service.activate(cloud, credential).getSystem().getId();
    }
    
    @Order(1)
    @Test
    public void createRoles() {
        roleBM.addPermissionsToRole(adminId, activeGit, Arrays.asList(new ExternalPermissionId("ADMIN")));
        roleBM.addResourcesToRole(adminId, activeGit, Arrays.asList(ExternalResourceId.newExternalResourceId("FooTeams", "Project")));
        
        roleBM.addPermissionsToRole(adminId, activateConfluence, Arrays.asList(new ExternalPermissionId("ADMIN")));
        roleBM.addResourcesToRole(adminId, activateConfluence, Arrays.asList(new ExternalResourceId("Space_Team_1"), new ExternalResourceId("SpaceTeam_2")));
        
        roleBM.addPermissionsToRole(adminId, activeCloud, Arrays.asList(new ExternalPermissionId("ROOT")));
        roleBM.addResourcesToRole(adminId, activeCloud, Arrays.asList(new ExternalResourceId("Cloud_Team_1"), new ExternalResourceId("Cloud_Team_2")));
        
        trx.execute((state) -> {
            RoleBE roleAdmin = roleBM.get(adminId);
            SystemRoleBE systemRole = roleAdmin.getSystemRoles().get(0);
            assertEquals(activeGit, systemRole.getSystem().getId());
            assertEquals(1, systemRole.getPermissions().size());
            assertEquals(1, systemRole.getResources().size());
            
            systemRole = roleAdmin.getSystemRoles().get(1);
            assertEquals(activateConfluence, systemRole.getSystem().getId());
            assertEquals(1, systemRole.getPermissions().size());
            System.out.println(systemRole.getResources());
            assertEquals(2, systemRole.getResources().size());
            
            systemRole = roleAdmin.getSystemRoles().get(2);
            assertEquals(activeCloud, systemRole.getSystem().getId());
            assertEquals(2, systemRole.getResources().size());
            return null;
        });
    }
    
    // TODO should be moved later
    @Autowired ProvisioningBM provisioningBM;
    @Autowired IdentityBM identityBM;
    @Order(2)
    @Test
    public void assignRoles() throws ConnectorException {
        IdentityBE admin = identityBM.createIdentity("ADMIN");
        provisioningBM.assignRole(admin.getId(), adminId);
        
        System.out.println("CONFLUENCE");
        helper.getExampleConnector(activateConfluence).print();
        System.out.println("CLOUD");
        helper.getExampleConnector(this.activeCloud).print();
        System.out.println("GIT");
        helper.getExampleConnector(this.activeGit).print();
    }
}