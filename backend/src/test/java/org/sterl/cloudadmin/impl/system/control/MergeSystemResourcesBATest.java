package org.sterl.cloudadmin.impl.system.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.system.control.MergeSystemResourcesBA;
import org.sterl.cloudadmin.impl.system.dao.SystemResourceDAO;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

@ExtendWith(MockitoExtension.class)
class MergeSystemResourcesBATest {
    
    @Mock SystemResourceDAO resourceDAO;
    @InjectMocks MergeSystemResourcesBA service;
    final SystemBE s = new SystemBE(new SystemId(1L));
    
    @Test
    void testMergeEmptyResources() {
        List<SystemResourceBE> current = new ArrayList<>(); 
        List<SystemResourceBE> toSet = new ArrayList<>();
        
        service.merge(current, toSet, null);
        
        assertEquals(0, current.size());
        Mockito.verify(resourceDAO, never()).save(any());
    }
    
    @Test
    void testMergeNewResources() {
        when(resourceDAO.save(any())).then((i) -> i.getArgument(0));

        List<SystemResourceBE> current = newList(); 
        List<SystemResourceBE> toSet = newList(
                new SystemResourceBE(s, "PEOPLE", "TABLE"), new SystemResourceBE(s, "PEOPLE", "VIEW"));
        
        service.merge(current, toSet, null);
        
        assertEquals(2, current.size());
        Mockito.verify(resourceDAO, times(2)).save(any());
    }
    
    @Test
    void testMergeRemoveResources() {
        List<SystemResourceBE> current = newList(
                new SystemResourceBE(s, "PEOPLE", "TABLE"), new SystemResourceBE(s, "PEOPLE", "VIEW")); 
        List<SystemResourceBE> toSet = newList();
        
        service.merge(current, toSet, null);
        
        assertEquals(0, current.size());
        Mockito.verify(resourceDAO, times(2)).delete(any());
    }
    
    @Test
    void testMergeResources() {
        when(resourceDAO.save(any())).then((i) -> i.getArgument(0));

        final SystemResourceBE toRemove = new SystemResourceBE(s, "RAT", "TABLE");
        List<SystemResourceBE> current = newList(
                new SystemResourceBE(s, "PEOPLE", "TABLE"), new SystemResourceBE(s, "PEOPLE", "VIEW"),
                toRemove); 
        final SystemResourceBE toAdd = new SystemResourceBE(s, "PHONE", "TABLE");
        List<SystemResourceBE> toSet = newList(
                new SystemResourceBE(s, "PEOPLE", "TABLE"), new SystemResourceBE(s, "PEOPLE", "VIEW"),
                toAdd);
        
        service.merge(current, toSet, null);
        
        assertEquals(3, current.size());
        Mockito.verify(resourceDAO, times(1)).save(toAdd);
        Mockito.verify(resourceDAO, times(1)).delete(toRemove);
    }

    @SafeVarargs
    static <T> ArrayList<T> newList(T... values) {
        ArrayList<T> result = new ArrayList<>(values.length);
        for (T t : values) result.add(t);
        return result;
    }
}
