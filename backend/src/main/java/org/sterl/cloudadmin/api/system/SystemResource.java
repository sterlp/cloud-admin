package org.sterl.cloudadmin.api.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Data
public class SystemResource {
    /**
     * Optional reference to the stored system config.
     */
    private SystemId systemId;
    /**
     * Optional reference to the stored system permission in the system.
     */
    private SystemResourceId id;
    /**
     * Unique name of this permission in the system. The only needed value here. 
     * Consider to use Enums to implement it in a simple scenario.
     * 
     * Optional resource type to group resources.
     * <ul>
     *     <li>In a DB: VIEW, TABLE, etc.
     * </ul>
     */
    private ExternalResourceId externalId;
}