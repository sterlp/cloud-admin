package org.sterl.cloudadmin.role.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.identity.model.IdentityBE;
import org.sterl.cloudadmin.system.model.HasSystem;
import org.sterl.cloudadmin.system.model.SystemBE;
import org.sterl.cloudadmin.system.model.SystemPermissionBE;
import org.sterl.cloudadmin.system.model.SystemResourceBE;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "ROLE")
public class RoleBE {

    @Id @Column(length = 255)
    @NotNull 
    private RoleId id;
    /*
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "ROLE_TO_SYSTEM_ROLE", 
        joinColumns = { @JoinColumn(name = "ROLE_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "SYSTEM_ROLE_ID") },
        foreignKey = @ForeignKey(name = "FK_ROLE_TO_SYSTEM_ROLE"),
        inverseForeignKey = @ForeignKey(name = "FK_SYSTEM_ROLE_TO_ROLE"),
        uniqueConstraints = @UniqueConstraint(name = "UC_ROLE_TO_SYSTEM_ROLE", columnNames = {"ROLE_ID", "SYSTEM_ROLE_ID"})
    )*/
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SystemRoleBE> systemRoles = new ArrayList<>();
    
    @ManyToMany(mappedBy = "roles")
    private List<IdentityBE> identities;

    public RoleBE(@NotNull RoleId id) {
        super();
        this.id = id;
    }
    
    public Collection<SystemId> assignedSystems() {
        return this.systemRoles.stream().map(sr -> sr.getSystem().getId()).collect(Collectors.toSet());
    }

    /**
     * Merges the given {@link SystemResourceBE} by the system to the system role.
     * 
     * @return the changed {@link SystemRoleBE}s
     */
    public Set<SystemRoleBE> addResources(List<SystemResourceBE> resources) {
        final Set<SystemRoleBE> result = new LinkedHashSet<>();
        
        for (SystemResourceBE sr : resources) {
            SystemRoleBE systemRole = findOrAdd(sr);
            if (systemRole.add(sr)) result.add(systemRole);
        }
        return result;
    }
    /**
     * Merges the given {@link SystemPermissionBE} by the system to the system role.
     * 
     * @return the changed {@link SystemRoleBE}s
     */
    public Set<SystemRoleBE> addPermissions(List<SystemPermissionBE> permission) {
        final Set<SystemRoleBE> result = new LinkedHashSet<>();
        
        for (SystemPermissionBE sp : permission) {
            SystemRoleBE systemRole = findOrAdd(sp);
            if (systemRole.add(sp)) result.add(systemRole);
        }
        return result;
    }
    
    private <T extends HasSystem> SystemRoleBE findOrAdd(T match) {
        SystemRoleBE found;
        if (systemRoles.isEmpty()) {
            found = new SystemRoleBE(this, match.getSystem());
            this.systemRoles.add(found);
        } else {
            Optional<SystemRoleBE> systemRole = systemRoles.stream()
                    .filter(e -> e.getSystem().equals(match.getSystem()))
                    .findFirst();
            
            if (systemRole.isPresent()) {
                found = systemRole.get();
            } else {
                found = new SystemRoleBE(this, match.getSystem());
                this.systemRoles.add(found);
            }
        }
        return found;
    }
}