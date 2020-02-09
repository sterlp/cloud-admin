package org.sterl.cloudadmin.impl.identity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;

@RepositoryRestResource(path = "identity", collectionResourceRel = "identities", itemResourceRel = "identity")
public interface IdentityDAO extends JpaRepository<IdentityBE, IdentityId> {

}
