package org.sterl.cloudadmin.api.connector;

import java.util.List;

import org.sterl.cloudadmin.api.system.ExternalAccountId;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemAccount;
import org.sterl.cloudadmin.api.system.SystemPermission;
import org.sterl.cloudadmin.api.system.SystemResource;
import org.sterl.cloudadmin.api.system.SystemResourcePermissions;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;

/**
 * Minimal requirement to an connector.
 */
public interface SimpleConnector extends AutoCloseable {
    /**
     * Do a connection check, return <code>false</code> if the connector is currently not available
     * @return <code>true</code> ready for work, otherwise <code>false</code>
     * @throws ConnectorException if the check went wrong
     */
    boolean isConnected() throws ConnectorException;
    /**
     * Return a list of all accounts in the system.
     */
    List<SystemAccount> getAllAccounts() throws ConnectorException;
    
    /**
     * Return the list of all resources in the system.
     * @return the list {@link SystemResource} in the given system. Maybe empty.
     */
    List<SystemResource> getAllResources() throws ConnectorException;
    /**
     * Returns a list of supported permissions by the given connector. These are used to be assigned to roles.
     * @return the List of {@link SystemPermission} in a managed system.
     */
    List<ExternalPermissionId> getAllPermissions() throws ConnectorException;
    /**
     * Used for a sync, if supported, to get all resources currently assigned to an account.
     * @param accountId the {@link ExternalAccountId}, never <code>null</code>
     * @return the currently assigned permissions and resources to the {@link SystemAccount}
     */
    List<SystemResourcePermissions> getAccountPermissions(ExternalAccountId accountId) throws ConnectorException;
    /**
     * This method is used to sync one resource with the system. Basically to return all currently assigned accounts
     * to a given resource.
     * <b>This method is used in combination with the {@link #getAccountPermissions(ExternalAccountId)} to get all permissions for an account.</b>
     * 
     * Some systems may not support both methods.
     * 
     * @param resourceId {@link ExternalResourceId} to get the permissions
     * @param type the optional type, maybe <code>null</code> 
     * @return the current set permissions on this object
     * @see #getAccountPermissions(ExternalAccountId)
     */
    List<SystemResourcePermissions> getResourcePermissions(ExternalResourceId resourceId) throws ConnectorException;
    
    /**
     * This method is responsible to set the permissions for an account
     * @param permissions {@link SystemResourcePermissions} to apply
     * @throws ConnectorException if it went's wrong
     */
    void setPermissions(SystemResourcePermissions permissions) throws ConnectorException;
}
