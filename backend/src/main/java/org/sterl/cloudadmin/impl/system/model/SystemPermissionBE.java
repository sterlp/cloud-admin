package org.sterl.cloudadmin.impl.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.SystemPermissionId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Any system may have multiple permissions e.g.:
 * <ul>
 *     <li>Database: SELECT, UPDATE
 *  <li>Confluence: ADMIN, MEMBER
 *  <li>OpenShift: ADMIN, USER
 * </ul>
 */
@Data @NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"system"})
@Accessors(chain = true)
@Entity 
@Table(name = "SYSTEM_PERMISSION", uniqueConstraints = @UniqueConstraint(name = "UC_SYSTEM_PERMISSION_NAME", columnNames = {"name", "SYSTEM_ID"}))
public class SystemPermissionBE implements HasSystem {
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_permission_id_generator")
    @GenericGenerator(name = "system_permission_id_generator", strategy = "org.sterl.cloudadmin.impl.system.model.jpa.SystemPermissionIdSequenceGenerator")
    @Id @Column(updatable = false)
    private SystemPermissionId id;

    @NotNull @Column(updatable = false, length = 255)
    private ExternalPermissionId name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SYSTEM_ID", nullable = false, updatable = false, 
                foreignKey = @ForeignKey(name = "FK_SYSTEM_PERMISSION_TO_SYSTEM"))
    private SystemBE system;

    public SystemPermissionBE(@NotNull ExternalPermissionId name) {
        this.name = name;
    }
    public SystemPermissionBE(@NotNull ExternalPermissionId name, SystemBE system) {
        this.name = name;
        this.system = system;
    }
    
    public boolean isSamePermission(SystemPermissionBE p) {
        if (p == null) return false;
        if (this == p) return true;
        if (this.id != null && p.getId() != null && this.id.equals(p.getId())) return true;
        return this.name.equals(p.getName()) && this.system.equals(p.getSystem());
    }
}