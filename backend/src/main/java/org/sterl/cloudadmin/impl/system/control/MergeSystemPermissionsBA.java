package org.sterl.cloudadmin.impl.system.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.impl.system.dao.SystemPermissionDAO;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemPermissionBE;

@Component
@Transactional(propagation = Propagation.MANDATORY)
class MergeSystemPermissionsBA extends AbstractMergeBA<SystemPermissionBE, SystemBE> {

    @Autowired SystemPermissionDAO permissionDAO;
    
    static List<SystemPermissionBE> newPermissions(List<ExternalPermissionId> ids) {
        final List<SystemPermissionBE> result = new ArrayList<SystemPermissionBE>(ids.size());
        ids.forEach(id -> result.add(new SystemPermissionBE(id)));
        return result;
    }
    
    @Override
    protected JpaRepository<SystemPermissionBE, ?> getRepository() {
        return permissionDAO;
    }

    @Override
    protected boolean isSame(SystemPermissionBE currentValue, SystemPermissionBE newValue) {
        return currentValue.getName().equals(newValue.getName());
    }

    @Override
    protected void beforeRemove(SystemPermissionBE currentValue, SystemBE root) {
    }

    @Override
    protected void beforeAdd(SystemPermissionBE newValue, SystemBE root) {
        newValue.setSystem(root);
    }
}