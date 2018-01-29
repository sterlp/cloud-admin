package org.sterl.cloud.admin.impl.common.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor
public class CredentialsBE {
    private String user;
    private String password;
}