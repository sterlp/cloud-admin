package org.sterl.cloudadmin.impl.system.model;

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

import org.sterl.cloudadmin.api.system.SystemCredentialId;
import org.sterl.cloudadmin.api.system.SystemCredentialType;
import org.sterl.cloudadmin.impl.common.id.jpa.EntityWithId;
import org.sterl.cloudadmin.impl.system.converter.SystemCredentialIdConverter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true)
@ToString(exclude = "password")
@Entity
@Table(name = "SYSTEM_CREDENTIAL", indexes = @Index(name = "IDX_SYSTEM_CREDENTIAL_NAME", columnList = "name", unique = false))
public class SystemCredentialBE extends EntityWithId<SystemCredentialId, Long> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(updatable = false)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private SystemCredentialType type = SystemCredentialType.BASIC;
    @NotNull @Size(min = 1, max = 255)
    private String name;
    @Size(max = 255)
    private String user;
    @Size(max = 255)
    private String password;
    
    public SystemCredentialBE() {
        super(SystemCredentialIdConverter.INSTANCE);
    }
    
    public SystemCredentialBE(String name) {
        this();
        this.name = name;
    }

}