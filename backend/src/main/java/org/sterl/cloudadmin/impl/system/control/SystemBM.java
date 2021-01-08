package org.sterl.cloudadmin.impl.system.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.api.system.ExternalAccountId;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.common.annotation.BusinessManager;
import org.sterl.cloudadmin.impl.common.id.Id;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;
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

@BusinessManager
//@Transactional
public class SystemBM {

    @Autowired SystemDAO systemDAO;
    @Autowired SystemPermissionDAO permissionDAO;
    @Autowired SystemResourceDAO resourcesDAO;
    @Autowired SystemAccountDAO systemAccountDAO;
    @Autowired SystemCredentialDAO systemCredentialDAO;
    @Autowired MergeSystemResourcesBA mergeResourcesBA;
    @Autowired MergeSystemPermissionsBA mergePermissionsBA;
    @Autowired MergeSystemAccountsBA mergeAccountsBA;

    @PersistenceContext EntityManager em;
    
    public SystemAccountBE findBy(IdentityBE identity, SystemId systemId) {
        return systemAccountDAO.findByIdentityIdAndSystemId(identity.getId(), Id.valueOf(systemId));
    }
    public SystemAccountBE createAccount(ExternalAccountId name, IdentityBE identity, SystemId systemId) {
        SystemAccountBE result = new SystemAccountBE();
        result.setSystem(em.getReference(SystemBE.class, Id.valueOf(systemId)));
        result.setIdentity(identity);
        result.setName(name);
        return systemAccountDAO.saveAndFlush(result);
    }
    /**
     * Saves an {@link SystemBE} and it's {@link SystemCredentialBE}.
     * @return the saved system with the credential set.
     */
    public SystemBE save(SystemBE system, SystemCredentialBE credential) {
        credential = systemCredentialDAO.save(credential);
        system.setCredential(credential);
        system = systemDAO.save(system);
        return system;
    }

    /**
     * Sets for the given {@link SystemBE} the permissions and resources.
     */
    public SystemBE setPermissionsAndResources(
            @NotNull SystemId id, List<ExternalPermissionId> permissions,
            List<SystemResourceBE> resources) {

        final SystemBE result = systemDAO.findById(Id.valueOf(id)).get();
        
        mergePermissionsBA.merge(result.getPermissions(), MergeSystemPermissionsBA.newPermissions(permissions), result);
        mergeResourcesBA.merge(result.getResources(), resources, result);

        return result;
    }
    /**
     * Sets for the system the given accounts 
     */
    public SystemBE setAccounts(SystemId id, List<SystemAccountBE> accounts) {
        final SystemBE result = systemDAO.findById(Id.valueOf(id)).get();
        mergeAccountsBA.merge(result.getAccounts(), accounts, result);
        return result;
    }

    public List<SystemPermissionBE> getPermissions(@NotNull SystemId systemId, Collection<ExternalPermissionId> permissions) {
        return permissionDAO.findBySystemIdAndNames(Id.valueOf(systemId), permissions);
    }

    public List<SystemResourceBE> getResources(SystemId systemId, Collection<ExternalResourceId> resources) {
        List<SystemResourceBE> result = new ArrayList<>(resources.size());
        for (ExternalResourceId er : resources) {
            // TODO consider list load
            result.add(resourcesDAO.findBySystemIdAndNameAndType(Id.valueOf(systemId), er.getName(), er.getType()));
        }
        return result; 
    }
}