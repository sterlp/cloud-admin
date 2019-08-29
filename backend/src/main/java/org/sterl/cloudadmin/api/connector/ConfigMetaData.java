package org.sterl.cloudadmin.api.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * As the configuration of a connector may contain generic values, this helps a bit to define what we have.
 */
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ConfigMetaData {

    public enum Type {
        STRING,
        NUMBER,
        BOOLEAN
    }

    private String label;
    private String description;
    private String property;
    @Builder.Default
    private Type type = Type.STRING;
}