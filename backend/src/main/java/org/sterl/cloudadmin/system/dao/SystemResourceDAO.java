package org.sterl.cloudadmin.system.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.api.system.SystemResourceId;
import org.sterl.cloudadmin.system.model.SystemResourceBE;

@RepositoryRestResource(itemResourceRel = "systemResource", collectionResourceRel = "systemResources", path = "system-resources")
public interface SystemResourceDAO extends HasSystemDAO<SystemResourceBE, SystemResourceId, ExternalResourceId> {

    SystemResourceBE findBySystemIdAndNameAndType(SystemId systemId, String name, String type);
    
}