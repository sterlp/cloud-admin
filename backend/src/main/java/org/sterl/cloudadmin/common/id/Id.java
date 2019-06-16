package org.sterl.cloudadmin.common.id;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Unique ID type which identifies an object in the system.
 * 
 * @param <T> type of the value
 */
public interface Id<T> extends Serializable {
    
    /**
     * Return the value of the embedded data 
     */
    T getValue();
    
    static final Comparator<AbstractId<Long>> LONG_COMPERATOR = new Comparator<AbstractId<Long>>() {
        @Override
        public int compare(AbstractId<Long> o1, AbstractId<Long> o2) {
            if (o1 == null || o1.getValue() == null) return 1;
            if (o2 == null || o2.getValue() == null) return -1;
            return o1.getValue().compareTo(o2.getValue());
        }
    };
    
    /**
     * Save way to get a value of the {@link Id}
     * 
     * @param t the {@link AbstractId} type
     * @return the value of the {@link AbstractId} or <code>null</code>
     */
    static <T extends Id<V>, V> V valueOf(T t) {
        return t == null ? null : t.getValue();
    }
}