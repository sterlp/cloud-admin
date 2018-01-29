package org.sterl.cloud.admin.impl.db.service;

import java.io.File;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Collection;

import org.clapper.util.classutil.AbstractClassFilter;
import org.clapper.util.classutil.AndClassFilter;
import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;
import org.clapper.util.classutil.InterfaceOnlyClassFilter;
import org.clapper.util.classutil.NotClassFilter;
import org.clapper.util.classutil.SubclassClassFilter;
import org.junit.Ignore;
import org.junit.Test;

public class DbDriverBFTest {

    @Ignore // not longer in use
    @Test
    public void testFoo() {
        ClassFinder finder = new ClassFinder();
        finder.addClassPath();
        finder.add(new File("./"));

        ClassFilter filter =
            new AndClassFilter
                // Must not be an interface
                (new NotClassFilter (new InterfaceOnlyClassFilter()),

                // Must implement the ClassFilter interface
                new SubclassClassFilter (Driver.class),

                // Must not be abstract
                new NotClassFilter (new AbstractClassFilter()));

        Collection<ClassInfo> foundClasses = new ArrayList<ClassInfo>();
        finder.findClasses (foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            System.out.println ("Found " + classInfo.getClassName());
        }
    }
}
