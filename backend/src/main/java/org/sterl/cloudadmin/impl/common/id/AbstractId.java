package org.sterl.cloudadmin.impl.common.id;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@SuppressWarnings("serial")
@EqualsAndHashCode(of = "value")
@JsonSerialize(using = IdSerializer.class)
public abstract class AbstractId<T> implements Id<T> {

    @Getter
    protected final T value;
    
    public AbstractId(@NotNull T value) {
        super();
        Objects.requireNonNull(value, "Null not allowed for " + this.getClass().getSimpleName() + ".");
        this.value = value;
    }
    
    protected String toString;
    @Override
    public String toString() {
        if (toString == null) toString = String.valueOf(value); // getClass().getSimpleName() + "(" + value + ")";
        return toString;
    }
}