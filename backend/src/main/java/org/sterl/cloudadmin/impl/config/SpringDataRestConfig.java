package org.sterl.cloudadmin.impl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
      config.exposeIdsFor(IdentityBE.class);
    }
}
