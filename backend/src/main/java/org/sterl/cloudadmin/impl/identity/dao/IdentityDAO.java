package org.sterl.cloudadmin.impl.identity.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;
import org.sterl.cloudadmin.impl.identity.model.QIdentityBE;

@RepositoryRestResource(path = "identities", collectionResourceRel = "identities", itemResourceRel = "identity")
public interface IdentityDAO extends JpaRepository<IdentityBE, IdentityId>,
    QuerydslPredicateExecutor<IdentityBE>, QuerydslBinderCustomizer<QIdentityBE> {
    
    @RestResource()
    @Query("SELECT e FROM IdentityBE e WHERE e.name LIKE :value% OR e.firstName LIKE :value% OR e.lastName LIKE :value% OR e.email LIKE :value%")
    List<IdentityBE> all(@Param("value") String value, Pageable page);
    
    default void customize(QuerydslBindings bindings, QIdentityBE root) {
        System.out.println(bindings + " -> " + root);
    }
}
