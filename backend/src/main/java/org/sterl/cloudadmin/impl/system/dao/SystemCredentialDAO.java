package org.sterl.cloudadmin.impl.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.system.SystemCredentialId;
import org.sterl.cloudadmin.impl.system.model.SystemCredentialBE;

@RepositoryRestResource(itemResourceRel = "systemCredential", collectionResourceRel = "systemCredentials", path = "system-credentials")
public interface SystemCredentialDAO extends JpaRepository<SystemCredentialBE, Long> {

}
