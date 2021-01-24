package org.sterl.cloudadmin.api.system;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SystemCredential {
    private SystemCredentialId id;
    @NotNull
    private SystemCredentialType type = SystemCredentialType.BASIC;
    @NotNull @Size(min = 1, max = 255)
    private String name;
    @Size(max = 255)
    private String user;
    @Size(max = 255)
    private String password;
}