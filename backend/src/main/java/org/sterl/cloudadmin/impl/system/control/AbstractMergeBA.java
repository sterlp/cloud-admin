package org.sterl.cloudadmin.impl.system.control;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.data.jpa.repository.JpaRepository;

abstract class AbstractMergeBA<E, R> {

    protected abstract JpaRepository<E, ?> getRepository();
    protected abstract boolean isSame(E currentValue, E newValue);
    protected abstract void beforeRemove(E currentValue, R root);
    protected abstract void beforeAdd(E newValue, R root);

    /**
     * Merges the given two list using the {@link R} as root
     * to attach new entities.s
     */
    void merge(Collection<E> current, Collection<E> toSet, R root) {
        if (current.isEmpty() && toSet.isEmpty()) return;

        final JpaRepository<E, ?> repoDAO = getRepository();

        final Iterator<E> currentRes = current.iterator();
        while (currentRes.hasNext()) {
            E curR = currentRes.next();
            boolean remove = true;
            // check if the known resource is part of the resources to Set
            final Iterator<E> newRes = toSet.iterator();
            while (newRes.hasNext()) {
                E newR = newRes.next();
                if (isSame(curR, newR)) {
                    remove = false;
                    newRes.remove();
                    break;
                }
            }
            // if not found, we remove it, as it is not part of the "toSet" list
            if (remove) {
                beforeRemove(curR, root);
                currentRes.remove();
                repoDAO.delete(curR);
            }
        }
        // add the remaining one
        for (E newResources : toSet) {
            beforeAdd(newResources, root);
            current.add(repoDAO.save(newResources));
        }
    }
}