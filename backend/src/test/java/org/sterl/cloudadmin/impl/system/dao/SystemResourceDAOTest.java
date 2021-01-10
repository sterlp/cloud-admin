package org.sterl.cloudadmin.impl.system.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class SystemResourceDAOTest {

    @Autowired
    SystemDAO systemDao;
    @Autowired
    SystemResourceDAO systemResourceDao;

    @Test
    void testFindBySystemIdAndNameAndType() {
        final var system = systemDao.save(new SystemBE("DB", "Foo_Connector"));
        final var table1 = systemResourceDao.save(new SystemResourceBE(new ExternalResourceId("Table1"), system));
        final var table2 = systemResourceDao.save(new SystemResourceBE(new ExternalResourceId("Table2", "Table"), system));
        
        
        var foundTable = systemResourceDao.findBySystemIdAndNameAndType(system.getId(), table2.getName(), table2.getType());
        assertThat(table2).isEqualTo(foundTable);

        foundTable = systemResourceDao.findBySystemIdAndNameAndType(system.getId(), table1.getName(), table1.getType());
        assertThat(table1).isEqualTo(foundTable);
    }

}
