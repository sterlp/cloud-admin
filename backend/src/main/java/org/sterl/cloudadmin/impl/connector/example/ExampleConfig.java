package org.sterl.cloudadmin.impl.connector.example;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ExampleConfig {

    /**
     * Comma separated list of supported resources (colon type is optional) 
     * e.g.: PERSONS:TABLE, ADDRESS:TABLE, SALARY:VIEW
     */
    @NotNull @Size(min = 1)
    private final String resource;
    
    /**
     * Comma separated list of supported permissions e.g.: READ, WRITE
     */
    @NotNull @Size(min = 1)
    private final String permissions;
}
