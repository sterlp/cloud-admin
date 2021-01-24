package org.sterl.cloudadmin.api.identity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Identity {
    private IdentityId id;
    /**
     * The unique name of this identity in the system
     */
    @NotNull @Size(min = 2, max = 1024)
    private String name;
    
    @Size(max = 64)
    private String firstName;
    @Size(max = 64)
    private String lastName;
    
    @Size(max = 128) @Email
    private String email;
}
