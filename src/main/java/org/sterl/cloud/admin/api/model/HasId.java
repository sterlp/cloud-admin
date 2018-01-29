package org.sterl.cloud.admin.api.model;

import org.sterl.cloud.admin.api.common.Id;

/**
 * Interface for model classes having a string {@link Id}
 */
public interface HasId<T extends Id<?>> {
    /**
     * Returns the strong id
     * @return the strong id of the given class
     */
    T getStrongId();
}
