package org.sterl.cloud.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.sterl.cloud.admin.impl.common.converter.IdConverterFactory;

@SpringBootApplication
public class EasyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyAdminApplication.class, args);
    }
    @Autowired
    protected void registerConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new IdConverterFactory());
    }
}
