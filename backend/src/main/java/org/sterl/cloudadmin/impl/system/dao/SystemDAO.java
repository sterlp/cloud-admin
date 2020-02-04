package org.sterl.cloudadmin.impl.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.system.model.SystemBE;

@RepositoryRestResource(itemResourceRel = "system", collectionResourceRel = "systems", path = "systems")
public interface SystemDAO extends JpaRepository<SystemBE, SystemId> {

    List<SystemBE> findByName(String name);
}
