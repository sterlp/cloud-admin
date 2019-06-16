package org.sterl.cloudadmin.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.sterl.cloudadmin.api.system.SystemCredentialId;
import org.sterl.cloudadmin.api.system.SystemCredentialType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data @NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@ToString(exclude = "password")
@Entity
@Table(name = "SYSTEM_CREDENTIAL", indexes = @Index(name = "IDX_SYSTEM_CREDENTIAL_NAME", columnList = "name", unique = false))
public class SystemCredentialBE {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_credential_id_generator")
    @GenericGenerator(name = "system_credential_id_generator", strategy = "org.sterl.cloudadmin.system.model.jpa.SystemCredentialIdSequenceGenerator")
    @Id @Column(updatable = false)
    private SystemCredentialId id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private SystemCredentialType type = SystemCredentialType.BASIC;
    @NotNull @Size(min = 1, max = 255)
    private String name;
    @Size(max = 255)
    private String user;
    @Size(max = 255)
    private String password;
    
    public SystemCredentialBE(String name) {
        this.name = name;
    }
}