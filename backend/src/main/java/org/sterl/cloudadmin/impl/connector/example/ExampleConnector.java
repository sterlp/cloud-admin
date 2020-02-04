package org.sterl.cloudadmin.impl.connector.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.system.ExternalAccountId;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemAccount;
import org.sterl.cloudadmin.api.system.SystemResource;
import org.sterl.cloudadmin.api.system.SystemResourcePermissions;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ExampleConnector implements SimpleConnector {

    @Getter
    private final List<ExternalPermissionId> permissions = Collections.synchronizedList(new ArrayList<>());
    @Getter
    private final List<SystemResource> resources = Collections.synchronizedList(new ArrayList<>());
    @Getter
    private final List<SystemAccount> accounts = Collections.synchronizedList(new ArrayList<>());
    @Getter
    private final List<SystemResourcePermissions> data = Collections.synchronizedList(new ArrayList<>());
    
    public void print() {
        System.out.println("= Accounts =");
        for (SystemAccount systemAccount : accounts) {
            System.out.println(systemAccount);
        }
        System.out.println("= Permissions =");
        for (SystemResourcePermissions p : data) {
            System.out.println(p);
        }
    }
    
    public boolean hasPermissions() {
        return !permissions.isEmpty();
    }
    public List<ExternalPermissionId> getAllPermissions() {
        return new ArrayList<>(permissions);
    }
    public void setAllPermissions(String... permissions) {
        this.permissions.clear();
        for (String p : permissions) {
            this.permissions.add(new ExternalPermissionId(p.trim()));
        }
    }
    
    public boolean hasResources() {
        return !resources.isEmpty();
    }
    public List<SystemResource> getAllResources() {
        return new ArrayList<>(resources);
    }
    public void setAllResources(List<SystemResource> resources) {
        this.resources.clear();
        this.resources.addAll(resources);
    }
    
    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }
    public List<SystemAccount> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
    
    public void removeAllPermissions(ExternalAccountId accountId) {
        data.removeIf(v -> v.getAccount().getExternalId().equals(accountId));
    }
    
    public void setPermissions(SystemResourcePermissions permissions) {
        Optional<SystemResourcePermissions> currentPermission = data.stream().filter(
                existing -> existing.getAccount().equals(permissions.getAccount()) 
                    && (permissions.getResource() == null || permissions.getResource().equals(permissions.getResource())))
                .findFirst();
        
        if (currentPermission.isPresent()) {
            currentPermission.get().setPermissions(new ArrayList<>(permissions.getPermissions()));
        } else {
            data.add(permissions);
        }
    }
    @Override
    public void close() throws Exception {
        // nothing
    }
    @Override
    public List<SystemResourcePermissions> getAccountPermissions(ExternalAccountId accountId) {
        return this.data.stream().filter(p -> p.getAccount().getExternalId().equals(accountId)).collect(Collectors.toList());
    }
    @Override
    public List<SystemResourcePermissions> getResourcePermissions(ExternalResourceId resourceId)  {
        return this.data.stream().filter(p -> p.getResource().getExternalId().equals(resourceId)).collect(Collectors.toList());
    }
    @Override
    public boolean isConnected() throws ConnectorException {
        return true;
    }
}