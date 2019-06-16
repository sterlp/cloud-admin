package org.sterl.cloudadmin.identity.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import org.hibernate.annotations.GenericGenerator;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.role.model.RoleBE;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@Entity
@Table(name = "IDENTITY", uniqueConstraints = @UniqueConstraint(name = "UC_IDENTITY_NAME", columnNames = "name"))
public class IdentityBE {
    @GenericGenerator(name = "identity_id_generator", strategy = "org.sterl.cloudadmin.identity.model.jpa.IdentityIdSequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identity_id_generator")
    @Id
    @Column(updatable = false)
    private IdentityId id;
    /**
     * The unique name of this identity in the system
     */
    @NotNull @Size(min = 1, max = 1024)
    private String name;
    
    @Size(max = 64)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 64)
    @Column(name = "last_name")
    private String lastName;
    
    @Size(max = 128) @Email
    private String email;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "account_strategy", length = 25, nullable = false)
    private AccountGenerationStrategy accountStrategy = AccountGenerationStrategy.SAME_AS_IDENTITY_ID;
    
    @ManyToMany(cascade = {}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "IDENTITY_TO_ROLE", 
        joinColumns = { @JoinColumn(name = "IDENTITY_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") },
        foreignKey = @ForeignKey(name = "FK_IDENTITY_TO_ROLE"),
        inverseForeignKey = @ForeignKey(name = "FK_ROLE_TO_IDENTITY")
    )
    private List<RoleBE> roles;
    
    public IdentityBE(@NotNull @Size(min = 1, max = 1024) String name) {
        super();
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