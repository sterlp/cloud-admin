package org.sterl.cloudadmin.impl.system.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.sterl.cloudadmin.api.system.SystemId;

class SystemResourceBETest {

    @Test
    void testIsSameResource() {
        SystemBE s = new SystemBE(new SystemId(1L));
        SystemResourceBE r = new SystemResourceBE(s, "FOO", null);
        assertTrue(r.isSameResource(new SystemResourceBE(s, "FOO", null)));
        assertFalse(r.isSameResource(new SystemResourceBE(s, "FOO2", null)));
        assertFalse(r.isSameResource(new SystemResourceBE(s, "FOO", "BAR")));
        assertFalse(r.isSameResource(null));
        
        r = new SystemResourceBE(s, "FOO", "BAR");
        assertFalse(r.isSameResource(new SystemResourceBE(s, "FOO", null)));
        assertFalse(r.isSameResource(new SystemResourceBE(s, "FOO2", null)));
        assertTrue(r.isSameResource(new SystemResourceBE(s, "FOO", "BAR")));
        
        assertFalse(r.isSameResource(new SystemResourceBE(new SystemBE(new SystemId(2L)), 
                "FOO", "BAR")));
    }
}
