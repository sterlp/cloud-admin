package org.sterl.cloudadmin.impl.role.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemPermissionBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "SYSTEM_ROLE")
public class SystemRoleBE {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Size(max = 255)
    private String name;
    
    @ManyToOne(cascade = {}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private RoleBE role;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {})
    @JoinColumn(name = "SYSTEM_ID", nullable = false, updatable = false, 
                foreignKey = @ForeignKey(name = "FK_SYSTEM_ROLE_TO_SYSTEM"))
    private SystemBE system;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "SYSTEM_ROLE_TO_SYSTEM_PERMISSION", 
        joinColumns = { @JoinColumn(name = "SYSTEM_ROLE_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "SYSTEM_PERMISSION_ID") },
        foreignKey = @ForeignKey(name = "FK_SYSTEM_ROLE_TO_SYSTEM_PERMISSION"),
        inverseForeignKey = @ForeignKey(name = "FK_SYSTEM_PERMISSION_TO_SYSTEM_ROLE"),
        uniqueConstraints = @UniqueConstraint(name = "UC_ROLE_TO_SYSTEM_PERMISSION", columnNames = {"SYSTEM_ROLE_ID", "SYSTEM_PERMISSION_ID"})
    )
    private Set<SystemPermissionBE> permissions = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "ROLE_TO_SYSTEM_RESOURCE", 
        joinColumns = { @JoinColumn(name = "SYSTEM_ROLE_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "SYSTEM_RESOURCE_ID") },
        foreignKey = @ForeignKey(name = "FK_SYSTEM_ROLE_TO_SYSTEM_RESOURCES"),
        inverseForeignKey = @ForeignKey(name = "FK_SYSTEM_RESOURCES_TO_SYSTEM_ROLE"),
        uniqueConstraints = @UniqueConstraint(name = "UC_ROLE_TO_SYSTEM_RESOURCE", columnNames = {"SYSTEM_ROLE_ID", "SYSTEM_RESOURCE_ID"})
    )
    private Set<SystemResourceBE> resources = new LinkedHashSet<>();

    public SystemRoleBE(RoleBE role, SystemBE system) {
        super();
        this.role = role;
        this.system = system;
    }
    
    /**
     * Add the given {@link SystemResourceBE} to the system role
     * @return <code>true</code> if added, false if already present
     */
    public boolean add(SystemResourceBE r) {
        final boolean add = resources.isEmpty() || !resources.stream().filter(e -> e.isSameResource(r)).findAny().isPresent();
        if (add) this.resources.add(r);
        return add;
    }
    /**
     * Add the given {@link SystemPermissionBE} to the system role
     * @return <code>true</code> if added, false if already present
     */
    public boolean add(SystemPermissionBE p) {
        final boolean add = permissions.isEmpty() || !permissions.stream().filter(e -> e.isSamePermission(p)).findAny().isPresent();
        if (add) this.permissions.add(p);
        return add;
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + "(id = " + id + ", name = " + name 
                + ", system = " + (system == null ? null : system.getId()) 
                + ", role= " + (role == null ? null : role.getId())
                + ", permissions = " + permissions.size() + ", resources = " + permissions.size() + ")";
    }
}