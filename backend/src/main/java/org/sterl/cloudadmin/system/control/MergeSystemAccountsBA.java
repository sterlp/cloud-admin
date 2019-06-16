package org.sterl.cloudadmin.system.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.system.dao.SystemAccountDAO;
import org.sterl.cloudadmin.system.model.SystemAccountBE;
import org.sterl.cloudadmin.system.model.SystemBE;

@Component
@Transactional(propagation = Propagation.MANDATORY)
class MergeSystemAccountsBA extends AbstractMergeBA<SystemAccountBE, SystemBE> {

    @Autowired SystemAccountDAO accountDAO;
    @Override
    protected JpaRepository<SystemAccountBE, ?> getRepository() {
        return accountDAO;
    }

    @Override
    protected boolean isSame(SystemAccountBE currentValue, SystemAccountBE newValue) {
        return currentValue.getName().equals(newValue.getName());
    }

    @Override
    protected void beforeRemove(SystemAccountBE currentValue, SystemBE root) {
    }

    @Override
    protected void beforeAdd(SystemAccountBE newValue, SystemBE root) {
        newValue.setSystem(root);
    }
}