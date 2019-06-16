package org.sterl.cloudadmin.api.system;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A {@link SystemResourcePermissions} defines the {@link SystemAccount} permissions on a given resource.
 * 
 * If the resource is <code>null</code> the Sytem may not support 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Accessors(chain = true)
@Data
public class SystemResourcePermissions {
    /**
     * The {@link SystemAccount} which permissions should be modified or has the given permissions. 
     */
    @NotNull
    private SystemAccount account;
    /**
     * The permission itself
     */
    private List<SystemPermission> permissions = new ArrayList<>();
    /**
     * Optional resource on which the permissions should be applied or are set 
     */
    private SystemResource resource;
}