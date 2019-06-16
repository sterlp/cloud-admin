package org.sterl.cloudadmin.identity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.identity.model.IdentityBE;

@RepositoryRestResource(path = "identities", collectionResourceRel = "identities", itemResourceRel = "identity")
public interface IdentityDAO extends JpaRepository<IdentityBE, IdentityId> {

}
