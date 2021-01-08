package org.sterl.cloudadmin.impl.connector.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sterl.cloudadmin.api.connector.ConnectorProvider;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.System;
import org.sterl.cloudadmin.api.system.SystemCredential;
import org.sterl.cloudadmin.api.system.SystemResource;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExampleProvider implements ConnectorProvider<ExampleConnector> {

    @Autowired ObjectMapper objectMapper;
    @Autowired Validator validator;
    
    public void validateConfig(System system) {
        
        try {
            final ExampleConfig api = objectMapper.readValue(system.getConfig(), ExampleConfig.class);
            final Set<ConstraintViolation<ExampleConfig>> validationResult = validator.validate(api);
            if (validationResult != null && !validationResult.isEmpty()) {
                throw new ConstraintViolationException(validationResult);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    @Override
    public ExampleConnector create(System system, SystemCredential credential) throws ConnectorException {

        ExampleConfig api = null;
        final ExampleConnector result = new ExampleConnector();
        if (system.getConfig() != null && system.getConfig().length() > 1) {
            try {
                api = objectMapper.readValue(system.getConfig(), ExampleConfig.class);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to parse the configuration: " 
                        + system.getConfig(), e);
            }
            
            
            result.setAllPermissions(api.getPermissions().split(","));
            
            final List<SystemResource> managedResources = new ArrayList<>();
            final String[] resources = api.getResource().split(",");
            for (final String r : resources) {
                final SystemResource resource = new SystemResource();
                resource.setSystemId(system.getId());
                resource.setExternalId(ExternalResourceId.newExternalResourceId(r));
                managedResources.add(resource);
            }
            result.setAllResources(managedResources);
        }
        return result;
    }

    @Override
    public String getName() {
        return "Example in Memory Connector";
    }

    @Override
    public String getConnectorId() {
        return ExampleConnector.class.getSimpleName();
    }
}
