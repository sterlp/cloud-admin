package org.sterl.cloudadmin.impl.system.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.common.id.Id;
import org.sterl.cloudadmin.impl.system.dao.SystemResourceDAO;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

@Component
@Transactional(propagation = Propagation.MANDATORY)
class MergeSystemResourcesBA extends AbstractMergeBA<SystemResourceBE, SystemBE> {

    @PersistenceContext EntityManager em;
    @Autowired SystemResourceDAO resourceDAO;
    
    protected SystemResourceBE getOrCreate(SystemId systemId, ExternalResourceId resourceId) {
        var result = resourceDAO.findBySystemIdAndNameAndType(
                Id.valueOf(systemId), 
                resourceId.getName(), 
                resourceId.getType());

        if (result == null) {
            var system = em.getReference(SystemBE.class, Id.valueOf(systemId));
            result = resourceDAO.save(new SystemResourceBE(resourceId, system));
        }
        return result;
    }

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