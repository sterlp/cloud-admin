package org.sterl.cloudadmin.impl.system.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.system.ExternalAccountId;
import org.sterl.cloudadmin.impl.system.model.SystemAccountBE;

@RepositoryRestResource(itemResourceRel = "systemAccount", collectionResourceRel = "systemAccounts", path = "system-accounts")
public interface SystemAccountDAO extends HasSystemDAO<SystemAccountBE, Long, ExternalAccountId> {

    @Query("SELECT e FROM SystemAccountBE e WHERE e.identity.id = :id AND e.system.id = :systemId")
    SystemAccountBE findByIdentityIdAndSystemId(Long id, Long systemId);
}
