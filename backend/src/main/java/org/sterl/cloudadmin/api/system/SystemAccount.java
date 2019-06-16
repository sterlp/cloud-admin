package org.sterl.cloudadmin.api.system;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Data
public class SystemAccount {
    /**
     * <b>Optional</b> reference to the stored system config maybe <code>null</code>.
     */
    private SystemId systemId;
    /**
     * <b>Optional</b> reference to the internal stored account maybe <code>null</code>.
     */
    private SystemAccountId id;
    /**
     * Unique name of the account in the target system.
     */
    @NotNull
    private ExternalAccountId externalId;
    
    /**
     * Optional first name set in the system or to set. <code>null</code> means do nothing.
     */
    private String firstName;
    
    /**
     * Optional last name set in the system or to set. <code>null</code> means do nothing.
     */
    private String lastName;
    
    /**
     * Optional email set in the system or to set. <code>null</code> means do nothing.
     */
    private String email;
}