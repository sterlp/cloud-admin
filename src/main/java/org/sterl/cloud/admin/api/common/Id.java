package org.sterl.cloud.admin.api.common;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter @EqualsAndHashCode(of = "value")
@JsonSerialize(using = IdSerializer.class)
public abstract class Id<T> implements Serializable {
    private final T value;

    public Id(T value) {
        super();
        if (value == null) throw new NullPointerException("Given ID is null.");
        this.value = value;
    }
    private String toString;
    @Override
    public String toString() {
        if (toString == null) toString = getClass().getSimpleName() + "(" + value + ")";
        return toString;
    }
}