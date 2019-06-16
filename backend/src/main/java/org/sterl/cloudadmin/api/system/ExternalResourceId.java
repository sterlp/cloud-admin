package org.sterl.cloudadmin.api.system;

import javax.validation.constraints.NotNull;

import org.sterl.cloudadmin.common.id.AbstractId;

import lombok.Getter;

/**
 * Reference to one {@link SystemResource} in an external system.
 * 
 * A system resource contains a type and a name. The type is optional.
 * 
 * <ul>
 *     <li>name:type --> e.g. PERSONS:TABLE
 *     <li>Name: e.g. PERSONS
 *  <li>Type: e.g. TABLE
 * </ul>
 */
public class ExternalResourceId extends AbstractId<String> {
    private static final long serialVersionUID = 1065246214965692496L;
    public static final char SEPARATOR = ':';
    /** The type of the resource */
    @Getter
    private final String type;
    @Getter
    private final String name;
    
    public static ExternalResourceId newExternalResourceId(String value) {
        if (value == null) return null;
        
        final String[] resourceAndType = value.split(":", 2);
        value = resourceAndType[0].trim();
        String type = null; 
        if (resourceAndType.length > 1) {
            type = resourceAndType[1].trim();
        }
        return new ExternalResourceId(value, type); 
    }
    public static ExternalResourceId newExternalResourceId(String value, String type) {
        return value == null ? null : new ExternalResourceId(value, type); 
    }
    public ExternalResourceId(@NotNull String name) {
        this(name, null);
    }
    public ExternalResourceId(@NotNull String name, String type) {
        super(name + (type == null ? "" : SEPARATOR + type));
        this.name = name;
        this.type = type;

        if (name.indexOf(SEPARATOR, 0) > -1) {
            throw new IllegalArgumentException(SEPARATOR + " cannot be part of the value.");
        }
    }
}