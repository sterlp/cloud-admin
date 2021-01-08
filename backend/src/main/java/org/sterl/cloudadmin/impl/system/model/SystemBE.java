package org.sterl.cloudadmin.impl.system.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.common.id.jpa.EntityWithId;
import org.sterl.cloudadmin.impl.system.converter.SystemIdConverter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true)
@ToString(of = {"id", "name", "connectorId", "connectUrl"})
@Entity
@Table(name = "SYSTEM", indexes = @Index(name = "IDX_SYSTEM_CLASS_NAME", columnList = "connector_id"))
public class SystemBE extends EntityWithId<SystemId, Long> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(updatable = false)
    private Long id;
    
    @NotNull @Size(min = 1, max = 255)
    private String name;
    
    @NotNull @Size(min = 1, max = 512)
    @Column(name = "connector_id")
    private String connectorId;
    
    @Lob
    @Size(max = 1024)
    private String description;
    /**
     * Base HTTP URL or any other way to connect to the system
     */
    @Column(name = "connect_url")
    @Size(max = 255)
    private String connectUrl;
    
    @Version
    private Long version;
    
    @Column(columnDefinition = "TEXT")
    private String config;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "system", orphanRemoval = true)
    private List<SystemResourceBE> resources;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "system", orphanRemoval = true)
    private List<SystemPermissionBE> permissions;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "system", orphanRemoval = true)
    private List<SystemAccountBE> accounts;
    
    @NotNull
    @ManyToOne(optional = false, cascade = {}, fetch = FetchType.LAZY) 
    @JoinColumn(name="CREDENTIAL_ID", nullable=false, updatable=true,
                foreignKey = @ForeignKey(name = "FK_SYSTEM_TO_CREDENTIAL"))
    private SystemCredentialBE credential;
    
    public SystemBE() {
        super(SystemIdConverter.INSTANCE);
    }

    /**
     * Reference constructor
     */
    public SystemBE(SystemId id) {
        this();
        this.id = org.sterl.cloudadmin.impl.common.id.Id.valueOf(id);
    }
    public SystemBE(String name, String connectorId) {
        this();
        this.name = name;
        this.connectorId = connectorId;
    }
}