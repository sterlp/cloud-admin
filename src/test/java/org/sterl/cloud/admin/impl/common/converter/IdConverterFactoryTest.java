package org.sterl.cloud.admin.impl.common.converter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sterl.cloud.admin.api.db.DbConnectionId;
import org.sterl.cloud.admin.api.dbdriver.DbDriverId;
import org.sterl.cloud.admin.impl.common.converter.IdConverterFactory;

public class IdConverterFactoryTest {

    @Test
    public void testDbConnectionId() {
        IdConverterFactory.IdConverter<DbConnectionId> idConverter = new IdConverterFactory.IdConverter<>(DbConnectionId.class);
        
        assertEquals(null, idConverter.convert(null));
        assertEquals(new DbConnectionId(1L), idConverter.convert("1"));
        assertEquals(new DbConnectionId(1L), idConverter.convert(1));
        assertEquals(new DbConnectionId(1L), idConverter.convert(1L));
    }
    
    @Test
    public void testDbDriverId() {
        IdConverterFactory.IdConverter<DbDriverId> idConverter = new IdConverterFactory.IdConverter<>(DbDriverId.class);
        
        assertEquals(null, idConverter.convert(null));
        assertEquals(new DbDriverId("1"), idConverter.convert("1"));
        assertEquals(new DbDriverId("1"), idConverter.convert(1));
        assertEquals(new DbDriverId("1"), idConverter.convert(1L));
    }
}
