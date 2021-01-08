package org.sterl.cloudadmin.api.system;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.sterl.cloudadmin.impl.common.id.ETag;

import lombok.Data;

@Data
public class System {
    private SystemId id;
    
    @NotNull @Size(min = 1, max = 255)
    private String name;
    
    @Size(max = 1024)
    private String description;
    /**
     * Base HTTP URL or any other way to connect to the system
     */
    @Size(max = 255)
    private String connectUrl;
    
    private String config;

    private ETag version;
    /**
     * Required credential reference if a new system should be created.
     */
    private SystemCredentialId credentialId;
}
