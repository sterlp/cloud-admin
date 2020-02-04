package org.sterl.cloudadmin.impl.system.model;

import java.time.Instant;

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
import org.sterl.cloudadmin.api.system.ExternalAccountId;
import org.sterl.cloudadmin.api.system.SystemAccountId;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Any system may have several accounts
 */
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "locked"})
@Accessors(chain = true)
@Entity
@Table(name = "SYSTEM_ACCOUNT", uniqueConstraints = {
    @UniqueConstraint(name = "UC_SYSTEM_ACCOUNT_NAME", columnNames = {"name", "SYSTEM_ID"}),
    @UniqueConstraint(name = "UC_IDENTITY_SYSTEM_ACCOUNT", columnNames = {"IDENTITY_ID", "SYSTEM_ID"}),
})
public class SystemAccountBE implements HasSystem {
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_account_id_generator")
    @GenericGenerator(name = "system_account_id_generator", strategy = "org.sterl.cloudadmin.impl.system.model.jpa.SystemAccountIdSequenceGenerator")
    @Id @Column(updatable = false)
    private SystemAccountId id;

    @NotNull
    private ExternalAccountId name;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SYSTEM_ID", nullable = false, updatable = false, 
                foreignKey = @ForeignKey(name = "FK_SYSTEM_ACCOUNT_TO_SYSTEM"))
    private SystemBE system;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "IDENTITY_ID", nullable = true, updatable = true, 
                foreignKey = @ForeignKey(name = "FK_SYSTEM_ACCOUNT_TO_IDENTITY"))
    private IdentityBE identity;
    
    @Column(name = "valid_from")
    private Instant validFrom;
    @Column(name = "valid_to")
    private Instant validTo;
    
    private boolean locked = false;
}