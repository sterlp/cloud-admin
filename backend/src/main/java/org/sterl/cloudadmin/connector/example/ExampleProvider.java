package org.sterl.cloudadmin.connector.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sterl.cloudadmin.api.connector.ConfigMetaData;
import org.sterl.cloudadmin.api.connector.ConnectorProvider;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.System;
import org.sterl.cloudadmin.api.system.SystemCredential;
import org.sterl.cloudadmin.api.system.SystemResource;
import org.sterl.cloudadmin.connector.exception.ConnectorException;

public class ExampleProvider implements ConnectorProvider<ExampleConnector> {
    public static final String PERMISSIONS_PROP = "PERMISSIONS";
    /**
     * <li>name:type --> e.g. PERSONS:TABLE
     */
    public static final String RESOURCES_PROP = "RESOURCES";
    
    private static final List<ConfigMetaData> CONFIG_META =
            Arrays.asList(
                ConfigMetaData.builder().label("Permissions").property(PERMISSIONS_PROP).description("Comma separated list of supported permissions e.g.: READ, WRITE").build(),
                ConfigMetaData.builder().label("Resources").property(RESOURCES_PROP).description("Comma separated list of supported resources (colon type is optional) e.g.: PERSONS:TABLE, ADDRESS:TABLE, SALARY:VIEW").build()
            );
    
    @Override
    public ExampleConnector create(System system, SystemCredential credential) throws ConnectorException {
        ExampleConnector result = new ExampleConnector();
        final String permissions = system.getConfig().get(PERMISSIONS_PROP);
        if (permissions != null) {
            result.setAllPermissions(permissions.split(","));
        }
        
        List<SystemResource> managedResources = new ArrayList<>();
        final String resourcesString = system.getConfig().get(RESOURCES_PROP);
        if (resourcesString != null) {
            String[] resources = resourcesString.split(",");
            for (String r : resources) {
                SystemResource resource = new SystemResource();
                resource.setSystemId(system.getId());
                resource.setExternalId(ExternalResourceId.newExternalResourceId(r));
                managedResources.add(resource);
            }
            result.setAllResources(managedResources);
        }
        return result;
    }

    @Override
    public Class<ExampleConnector> getClassName() {
        return ExampleConnector.class;
    }

    @Override
    public String getName() {
        return "Example in Memory Connector";
    }

    @Override
    public List<ConfigMetaData> getConfigMeta() {
        return CONFIG_META;
    }
}
