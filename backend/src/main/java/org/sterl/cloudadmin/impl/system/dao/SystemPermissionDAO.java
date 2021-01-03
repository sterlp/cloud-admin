package org.sterl.cloudadmin.impl.system.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.api.system.SystemPermissionId;
import org.sterl.cloudadmin.impl.system.model.SystemPermissionBE;

@RepositoryRestResource(itemResourceRel = "systemPermission", collectionResourceRel = "systemPermissions", path = "system-permissions")
public interface SystemPermissionDAO extends HasSystemDAO<SystemPermissionBE, Long, ExternalPermissionId> {

    @Query("SELECT e from SystemPermissionBE e WHERE e.system.id = :systemId AND name IN (:names) ORDER BY name ASC")
    List<SystemPermissionBE> findBySystemIdAndNames(Long systemId, Collection<ExternalPermissionId> names);
}
