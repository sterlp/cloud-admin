package org.sterl.cloudadmin.impl.system.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

@RepositoryRestResource(itemResourceRel = "systemResource", collectionResourceRel = "systemResources", path = "system-resources")
public interface SystemResourceDAO extends HasSystemDAO<SystemResourceBE, Long, ExternalResourceId> {

    SystemResourceBE findBySystemIdAndNameAndType(Long systemId, String name, String type);
}
