package org.sterl.cloudadmin.impl.identity.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.api.system.ExternalAccountId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.common.annotation.BusinessManager;
import org.sterl.cloudadmin.impl.common.id.Id;
import org.sterl.cloudadmin.impl.identity.dao.IdentityDAO;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;
import org.sterl.cloudadmin.impl.role.model.RoleBE;
import org.sterl.cloudadmin.impl.role.model.SystemRoleBE;
import org.sterl.cloudadmin.impl.system.control.SystemBM;
import org.sterl.cloudadmin.impl.system.model.SystemAccountBE;

@BusinessManager
public class IdentityBM {

    @Autowired SystemBM systemBM;
    @Autowired IdentityDAO identityDAO;

    public IdentityBE assignRole(IdentityBE identity, RoleBE role) {
        identity.assignRole(role);
        return identityDAO.saveAndFlush(identity);
    }

    public SystemAccountBE getOrCreateSystemAccount(IdentityBE identity, SystemId systemId) {
        SystemAccountBE result = systemBM.findBy(identity, systemId);
        if (result == null) {
            result = systemBM.createAccount(new ExternalAccountId(identity.getName()), identity, systemId);
        }
        return result;
    }

    /**
     * Computes the list of {@link SystemRoleBE}s of an {@link IdentityBE} to the given {@link SystemId}.
     */
    public List<SystemRoleBE> getSystemRoles(IdentityBE identity, SystemId systemId) {
        List<SystemRoleBE> result = new ArrayList<>();
        for(RoleBE r : identity.getRoles()) {
            for (SystemRoleBE sr : r.getSystemRoles()) {
                if (sr.getSystem().getStrongId().equals(systemId)) result.add(sr);
            }
        }
        return result;
    }

    public IdentityBE createIdentity(String name) {
        return identityDAO.save(new IdentityBE(name));
    }

    public IdentityBE getIdentity(IdentityId identityId) {
        return identityDAO.getOne(Id.valueOf(identityId));
    }
}