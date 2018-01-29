package org.sterl.cloud.admin.impl.db.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.sterl.cloud.admin.api.db.DbConnectionId;
import org.sterl.cloud.admin.api.dbdriver.DbDriverId;
import org.sterl.cloud.admin.api.model.HasId;
import org.sterl.cloud.admin.impl.common.model.CredentialsBE;

import lombok.Data;

@Entity
@Table(name = "db_connection")
@Data
public class DbConnectionBE implements HasId<DbConnectionId> {
    
    @GeneratedValue
    @Id
    private Long id;
    @NotNull
    private String name;
    /** The JDBC driver to use -- we will always save one, even if we use a dbDriverId */
    @NotNull
    private String driver;
    @NotNull
    private String url;
    /** The DB schema to select by default */
    private String schema;
    
    private DbDriverId dbDriverId;
    
    @Embedded
    private CredentialsBE credentials;
    
    @Transient
    private Boolean connected;
    
    public CredentialsBE getCredentials() {
        if (credentials == null) credentials = new CredentialsBE();
        return credentials;
    }
    
    @Transient
    private transient DbConnectionId strongId;
    public DbConnectionId getStrongId() {
        if (id == null) return null;
        if (strongId == null) strongId = new DbConnectionId(id);
        return strongId;
    }
}
