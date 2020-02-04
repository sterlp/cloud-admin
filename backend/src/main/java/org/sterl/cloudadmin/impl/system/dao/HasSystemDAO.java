package org.sterl.cloudadmin.impl.system.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.system.model.SystemBE;

/**
 * Any {@link SystemBE} based resource should have the "system" property and a "name" property. 
 *
 * @param <T> the entity type
 * @param <ID> the id type
 */
@NoRepositoryBean
public interface HasSystemDAO<T, ID, NameType> extends JpaRepository<T, ID> {
    Page<T> findBySystemId(SystemId id, Pageable page);
    List<T> findByNameAndSystemId(NameType name, SystemId id);
}
