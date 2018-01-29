package org.sterl.cloud.admin.impl.db.api;

import org.sterl.cloud.admin.api.db.DbConnectionInfo;
import org.sterl.cloud.admin.api.db.UpdateDbConnectionCmd;
import org.sterl.cloud.admin.impl.common.converter.Converter;
import org.sterl.cloud.admin.impl.common.model.CredentialsBE;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;

/**
 * Type converter private to the API / BF
 */
class DbConverter {

    enum ToDbConnectionInfo implements Converter<DbConnectionBE, DbConnectionInfo> {
        INSTANCE;
        @Override
        public DbConnectionInfo convert(DbConnectionBE in) {
            if (in == null) return null;
            DbConnectionInfo result = new DbConnectionInfo();
            result.setId(in.getStrongId());
            result.setName(in.getName());
            result.setDriver(in.getDriver());
            result.setUrl(in.getUrl());
            result.setHasPassword(in.getCredentials().getPassword() == null ? Boolean.FALSE : Boolean.TRUE);
            result.setUser(in.getCredentials().getUser());
            result.setConnected(in.getConnected());
            result.setDbDriver(in.getDbDriverId());
            return result;
        }
    }
    
    enum ToDbConnectionBE implements Converter<UpdateDbConnectionCmd, DbConnectionBE> {
        INSTANCE;

        @Override
        public DbConnectionBE convert(UpdateDbConnectionCmd in) {
            if (in == null) return null;
            DbConnectionBE result = new DbConnectionBE();
            if (in.getId() != null) result.setId(in.getId().getValue());
            result.setName(in.getName());
            result.setCredentials(new CredentialsBE(in.getUser(), in.getPassword()));
            result.setDriver(in.getDriver());
            result.setDbDriverId(in.getDbDriver());
            result.setSchema(in.getSchema());
            result.setUrl(in.getUrl());
            return result;
        }
    }
}