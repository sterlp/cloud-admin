package org.sterl.cloudadmin.impl.system.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.system.SystemId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@ToString(of = {"id", "name", "className", "connectUrl"})
@Entity
@Table(name = "SYSTEM", indexes = @Index(name = "IDX_SYSTEM_CLASS_NAME", columnList = "class_name"))
public class SystemBE {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_id_generator")
    @GenericGenerator(name = "system_id_generator", strategy = "org.sterl.cloudadmin.impl.system.model.jpa.SystemIdSequenceGenerator")
    @Id @Column(updatable = false)
    private SystemId id;
    
    @NotNull @Size(min = 1, max = 255)
    private String name;
    
    @NotNull @Size(min = 1, max = 512)
    @Column(name = "class_name")
    private String className;
    
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

    @OrderBy("name")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "system")
    private Set<SystemConfigBE> config = new LinkedHashSet<>();
    
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
    
    /**
     * Reference constructor
     */
    public SystemBE(SystemId id) {
        this.id = id;
    }
    public SystemBE(String name, Class<? extends SimpleConnector> clazz) {
        this.name = name;
        this.className = clazz.getName();
    }
    
    public SystemBE addConfig(String name, String value) {
        SystemConfigBE cfg = config.stream().filter(c -> c.isNamed(name))
            .findFirst()
            .orElseGet(() -> new SystemConfigBE(this, name));
        cfg.setValue(value);
        this.config.add(cfg);
        return this;
    }
}