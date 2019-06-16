package org.sterl.cloudadmin.system.model;

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
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemResourceId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Any system may have multiple resources e.g.:
 * <ul>
 *     <li>Database: Tables
 *  <li>Confluence: Spaces
 *  <li>OpenShift: Projects
 * </ul>
 */
@Data @NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@ToString(exclude = {"system"})
@Entity
@Table(name = "SYSTEM_RESOURCE", uniqueConstraints = @UniqueConstraint(name = "UC_SYSTEM_RESOURCE_NAME_TYPE", columnNames = {"name", "type", "SYSTEM_ID"}))
public class SystemResourceBE implements HasSystem {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_resource_id_generator")
    @GenericGenerator(name = "system_resource_id_generator", strategy = "org.sterl.cloudadmin.system.model.jpa.SystemResourceIdSequenceGenerator")
    @Id @Column(updatable = false)
    private SystemResourceId id;
    
    @NotNull
    @Column(length = 255)
    private String name;
    /**
     * Optional resource type to group resources.
     * <ul>
     *     <li>In a DB: VIEW, TABLE, etc.
     * </ul>
     */
    @Column(length = 255)
    private String type;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SYSTEM_ID", nullable = false, updatable = false, 
                foreignKey = @ForeignKey(name = "FK_SYSTEM_RESOURCE_TO_SYSTEM"))
    private SystemBE system;
    
    public SystemResourceBE(@NotNull SystemBE system, @NotNull String name, String type) {
        super();
        this.system = system;
        this.name = name;
        this.type = type;
    }
    public SystemResourceBE(@NotNull ExternalResourceId erId, @NotNull SystemBE system) {
        this.name = erId.getName();
        this.type = erId.getType();
        this.system = system;
    }

    public ExternalResourceId asExternalResourceId() {
        return new ExternalResourceId(name, type);
    }
    /**
     * Checks if the external name and type is equal.
     */
    public boolean isSameResource(SystemResourceBE r) {
        if (r == null || name == null) return false;
        if (this == r) return true;
        if (this.id != null && r.getId() != null && this.id.equals(r.getId())) return true;

        boolean result;
        if (name.equals(r.getName()) && this.system.equals(r.getSystem())) {
            if (type == null && r.getType() == null) {
                result = true;
            } else if (type != null) {
                result = type.equals(r.getType());
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }
}