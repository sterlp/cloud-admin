package org.sterl.cloudadmin.impl.system.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.sterl.cloudadmin.api.system.SystemId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@Entity
@Table(name = "SYSTEM_CONFIG")
public class SystemConfigBE implements HasSystem {
    @Embeddable
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SystemConfigPK implements Serializable {
        private static final long serialVersionUID = 1L;
        @Column(name = "system_id", updatable = false)
        private SystemId systemId;
        @Size(min = 1, max = 256)
        @Column(name = "name")
        private String name;
    }

    @EmbeddedId
    private SystemConfigPK id;

    @MapsId("system_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id", updatable = false)
    private SystemBE system;

    @Size(max = 512)
    private String value;
    
    @PrePersist
    void ensureSystemId() {
        if (this.system != null && this.id.getSystemId() == null) {
            this.id.setSystemId(system.getId());
        }
    }

    public SystemConfigBE(SystemBE system, String name) {
        super();
        Objects.requireNonNull(system, "SystemBE cannot be null!");
        this.system = system;
        this.id = new SystemConfigPK(system.getId(), name);
    }
    
    public boolean isNamed(String name) {
        if (name == null) return false;
        if (id == null || id.getName() == null) return false;
        return id.getName().equals(name);
    }
}