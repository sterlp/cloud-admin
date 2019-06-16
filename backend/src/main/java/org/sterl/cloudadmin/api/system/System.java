package org.sterl.cloudadmin.api.system;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.sterl.cloudadmin.common.id.ETag;

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
    
    private Map<String, String> config = new LinkedHashMap<>();

    private ETag version;
    /**
     * Required credential reference if a new system should be created.
     */
    private SystemCredentialId credentialId;
    
    public System addConfig(String name, String value) {
        this.config.put(name, value);
        return this;
    }
}
