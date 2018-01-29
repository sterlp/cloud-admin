package org.sterl.cloud.admin.api.dbdriver;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateUrlCmd {
    @NotNull
    private String url;
    private String driver;
    private DbDriverId dbDriver;
}