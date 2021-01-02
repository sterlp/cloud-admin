package org.sterl.cloudadmin.impl.connector.db;

import java.util.List;

import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.system.ExternalAccountId;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemAccount;
import org.sterl.cloudadmin.api.system.SystemResource;
import org.sterl.cloudadmin.api.system.SystemResourcePermissions;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;

public class PostgreConnector implements SimpleConnector {

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isConnected() throws ConnectorException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<SystemAccount> getAllAccounts() throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SystemResource> getAllResources() throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ExternalPermissionId> getAllPermissions() throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SystemResourcePermissions> getAccountPermissions(ExternalAccountId accountId)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SystemResourcePermissions> getResourcePermissions(ExternalResourceId resourceId)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPermissions(SystemResourcePermissions permissions) throws ConnectorException {
        // TODO Auto-generated method stub
        
    }

}
