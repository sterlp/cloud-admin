package org.sterl.cloudadmin.impl.system.converter;

import org.sterl.cloudadmin.api.system.SystemAccount;
import org.sterl.cloudadmin.api.system.SystemCredential;
import org.sterl.cloudadmin.api.system.SystemPermission;
import org.sterl.cloudadmin.api.system.SystemResource;
import org.sterl.cloudadmin.impl.common.converter.CloudConverter;
import org.sterl.cloudadmin.impl.common.id.ETag;
import org.sterl.cloudadmin.impl.system.model.SystemAccountBE;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemCredentialBE;
import org.sterl.cloudadmin.impl.system.model.SystemPermissionBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

public class SystemConverters {

    public enum ToSystem implements CloudConverter<SystemBE, org.sterl.cloudadmin.api.system.System> {
        INSTANCE;
        @Override
        public org.sterl.cloudadmin.api.system.System convert(SystemBE source) {
            if (source == null) return null;
            final org.sterl.cloudadmin.api.system.System result = new  org.sterl.cloudadmin.api.system.System();
            result.setConnectUrl(source.getConnectUrl());
            if (source.getCredential() != null) {
                result.setCredentialId(source.getCredential().getId());
            }
            result.setDescription(source.getDescription());
            result.setId(source.getId());
            result.setName(source.getName());
            if (source.getVersion() != null) {
                result.setVersion(new ETag(source.getVersion()));
            }
            if (source.getConfig() != null) {
                source.getConfig().forEach(c -> result.addConfig(c.getId().getName(), c.getValue()));
            }
            return result;
        }
    }
    
    public enum ToSystemCredential implements CloudConverter<SystemCredentialBE, SystemCredential> {
        INSTANCE;

        @Override
        public SystemCredential convert(SystemCredentialBE source) {
            if (source == null) return null;
            SystemCredential result = new SystemCredential();
            result.setId(source.getId());
            result.setName(source.getName());
            result.setPassword(source.getPassword());
            result.setType(source.getType());
            result.setUser(source.getUser());
            return result;
        }
    }

    public enum ToSystemPermission implements CloudConverter<SystemPermissionBE, SystemPermission> {
        INSTANCE;

        @Override
        public SystemPermission convert(SystemPermissionBE source) {
            if (source == null) return null;
            SystemPermission result = new SystemPermission();
            result.setExternalId(source.getName());
            result.setSystemId(source.getSystem().getId());
            result.setId(source.getId());
            return result;
        }
    }
    
    public enum ToSysteAccount implements CloudConverter<SystemAccountBE, SystemAccount> {
        INSTANCE;

        @Override
        public SystemAccount convert(SystemAccountBE source) {
            if (source == null) return null;
            SystemAccount result = new SystemAccount();
            result.setId(source.getId());
            result.setExternalId(source.getName());
            result.setEmail(source.getIdentity().getEmail());
            result.setFirstName(source.getIdentity().getFirstName());
            result.setLastName(source.getIdentity().getLastName());
            result.setSystemId(source.getSystem().getId());
            return result;
        }
    }
    
    public enum ToSystemResource implements CloudConverter<SystemResourceBE, SystemResource> {
        INSTANCE;

        @Override
        public SystemResource convert(SystemResourceBE source) {
            if (source == null) return null;
            SystemResource result = new SystemResource();
            result.setExternalId(source.asExternalResourceId());
            result.setId(source.getId());
            result.setSystemId(source.getSystem().getId());
            return result;
        }
    }
    
    public enum ToSystemResourceBE implements CloudConverter<SystemResource, SystemResourceBE> {
        INSTANCE;
        @Override
        public SystemResourceBE convert(SystemResource source) {
            if (source == null) return null;
            SystemResourceBE result = new SystemResourceBE();

            if (source.getExternalId() != null) {
                result.setName(source.getExternalId().getName());
                result.setType(source.getExternalId().getType());
            }

            if (source.getSystemId() != null) {
                result.setSystem(new SystemBE(source.getSystemId()));
            }
            return result;
        }
    }
    
    public enum ToSystemAccountBE implements CloudConverter<SystemAccount, SystemAccountBE> {
        INSTANCE;
        @Override
        public SystemAccountBE convert(SystemAccount source) {
            if (source == null) return null;
            SystemAccountBE result = new SystemAccountBE();
            result.setId(source.getId());
            result.setName(source.getExternalId());
            if (source.getSystemId() != null) {
                result.setSystem(new SystemBE(source.getSystemId()));
            }
            return result;
        }
    }
}