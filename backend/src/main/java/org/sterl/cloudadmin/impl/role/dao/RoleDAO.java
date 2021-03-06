package org.sterl.cloudadmin.impl.role.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.impl.role.model.RoleBE;

@RepositoryRestResource(itemResourceRel = "role", collectionResourceRel = "roles", path = "roles")
public interface RoleDAO extends JpaRepository<RoleBE, String> {

}
