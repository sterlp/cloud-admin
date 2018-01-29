package org.sterl.cloud.admin.api.db;

import org.sterl.cloud.admin.api.common.Id;
import org.sterl.cloud.admin.impl.common.converter.Converter;

public class DbConnectionId extends Id<Long> {
    private static final long serialVersionUID = 5588540003980971475L;
    public DbConnectionId(Long value) {
        super(value);
    }
    
    public enum LongToDbConnectionId implements Converter<Long, DbConnectionId> {
        INSTANCE;
        @Override
        public DbConnectionId convert(Long in) {
            return new DbConnectionId(in);
        }
    }
}