package org.sterl.cloudadmin.impl.identity.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.common.id.jpa.EntityWithId;
import org.sterl.cloudadmin.impl.identity.converter.IdentityIdConverter;
import org.sterl.cloudadmin.impl.role.model.RoleBE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(of = "name", callSuper = true)
@Entity
@Table(name = "IDENTITY", uniqueConstraints = @UniqueConstraint(name = "UC_IDENTITY_NAME", columnNames = "name"))
public class IdentityBE extends EntityWithId<IdentityId, Long> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    private Long id;
    /**
     * The unique name of this identity in the system
     */
    @NotNull @Size(min = 2, max = 1024)
    private String name;
    
    @Size(max = 64)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 64)
    @Column(name = "last_name")
    private String lastName;
    
    @Size(max = 128) @Email
    private String email;
    
    @ManyToMany(cascade = {}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "IDENTITY_TO_ROLE", 
        joinColumns = { @JoinColumn(name = "IDENTITY_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") },
        foreignKey = @ForeignKey(name = "FK_IDENTITY_TO_ROLE"),
        inverseForeignKey = @ForeignKey(name = "FK_ROLE_TO_IDENTITY")
    )
    private List<RoleBE> roles;
    
    public IdentityBE() {
        super(IdentityIdConverter.INSTANCE);
    }
    public IdentityBE(@NotNull @Size(min = 1, max = 1024) String name) {
        this();
        this.name = name;
    }

    /**
     * Assigns a role, ensure the role is saved before.
     */
    public void assignRole(RoleBE role) {
        roles.add(role);
        role.getIdentities().add(this);
    }

    public void removeRole(RoleBE role) {
        roles.remove(role);
        role.getIdentities().remove(this);
    }

}