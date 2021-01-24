package org.sterl.cloudadmin.impl.identity.api;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterl.cloudadmin.api.identity.Identity;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.common.id.Id;
import org.sterl.cloudadmin.impl.identity.converter.ToIdentityConverter;
import org.sterl.cloudadmin.impl.identity.dao.IdentityDAO;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Identity", description = "Manage identities which may have accounts and roles assigned too.")
@RestController
public class IdentityBF {
    @Autowired Validator validator;
    @Autowired IdentityDAO identityDao;
    @Autowired ToIdentityConverter toIdentity;


    @GetMapping("api/identities")
    public Page<Identity> validate(@RequestParam(required = false) String query, 
            @PageableDefault(size = 20) @RequestParam(required = false) Pageable pageable) {
        return identityDao.findAll(pageable).map(i -> toIdentity.convert(i));
    }
    
    @GetMapping("api/identities/{id}")
    public ResponseEntity<Identity> get(@PathVariable Long id) {
        return ResponseEntity.of(identityDao.findById(id).map(i -> toIdentity.convert(i)));
    }
}
