package org.sterl.cloud.admin.impl.dbdriver.business;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Basically searches the class path for any class implementing {@link Driver}
 * @see http://software.clapper.org/classutil/
 */
@Component
class SearchDbDriverBA {
    private static Logger LOG = LoggerFactory.getLogger(SearchDbDriverBA.class);
    /** The drivers will not change during runtime -- as so we keep them cached here */
    private volatile Map<String, Driver> drivers = null;

    Map<String, Driver> execute() {
        Map<String, Driver> result;
        if (drivers == null) {
            result = findDrivers();
            synchronized(this) {
                drivers = new HashMap<>(result);
            }
        } else {
            result = new HashMap<>(drivers);
        }
        return result;
    }
    public Driver getDriver(String driver) {
        final Driver result = execute().get(driver);
        // TODO
        if (result == null) throw new RuntimeException("BD Driver " + driver + " not found.");
        return result;
    }
    /**
     * Resets the internal cache -- mostly used for tests
     */
    void clear() {
        synchronized (this) {
            drivers = null;
        }
    }

    private Map<String, Driver> findDrivers() {
        long time = System.currentTimeMillis();
        Map<String, Driver> result = new HashMap<>();
        /*
        ClassFinder finder = new ClassFinder();
        finder.addClassPath();
        final Collection<ClassInfo> foundClasses = new ArrayList<ClassInfo>();
        finder.findClasses (foundClasses, new ImplementsClassFilter(Driver.class));
        List<String> result = foundClasses.stream().map(ClassInfo::getClassName).collect(Collectors.toList());
        time = System.currentTimeMillis() - time;
        */
        Enumeration<Driver> d = DriverManager.getDrivers();
        while (d.hasMoreElements()) {
            Driver driver = d.nextElement();
            result.put(driver.getClass().getName(), driver);
        }
        LOG.info("Found {} DB driver in {} ms.", result.size(), time);
        return result;
    }
    /*
    @AllArgsConstructor
    static class ImplementsClassFilter implements ClassFilter {
        private final String clazz;
        public ImplementsClassFilter(Class<?> clazz) {
            this(clazz.getName());
        }
        @Override
        public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
            if (Modifier.isAbstract(classInfo.getModifier())) return false;
            final String[] interfaces = classInfo.getInterfaces();
            for (String i : interfaces) {
                if (clazz.equals(i)) return true;
            }
            return false;
        }
    }
    */
}