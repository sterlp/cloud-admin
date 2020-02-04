package org.sterl.cloudadmin.impl.system.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.impl.system.dao.SystemResourceDAO;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

@Component
@Transactional(propagation = Propagation.MANDATORY)
class MergeSystemResourcesBA extends AbstractMergeBA<SystemResourceBE, SystemBE> {

    @Autowired SystemResourceDAO resourceDAO;

    @Override
    protected JpaRepository<SystemResourceBE, ?> getRepository() {
        return resourceDAO;
    }

    @Override
    protected boolean isSame(SystemResourceBE currentValue, SystemResourceBE newValue) {
        return currentValue.isSameResource(newValue);
    }

    @Override
    protected void beforeRemove(SystemResourceBE currentValue, SystemBE root) {
    }

    @Override
    protected void beforeAdd(SystemResourceBE newValue, SystemBE root) {
        newValue.setSystem(root);
    }
}