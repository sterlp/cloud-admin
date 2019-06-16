package org.sterl.cloudadmin.api.system;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Data
public class SystemPermission {

    /**
     * Optional reference to the stored system config.
     */
    private SystemId systemId;
    /**
     * Optional reference to the stored system permission in the system.
     */
    private SystemPermissionId id;
    /**
     * Unique name of this permission in the system. The only needed value here. 
     * Consider to use Enums to implement it in a simple scenario.
     */
    @NotNull
    private ExternalPermissionId externalId;
}
